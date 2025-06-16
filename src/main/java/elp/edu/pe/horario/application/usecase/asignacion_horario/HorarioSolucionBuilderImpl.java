package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.model.*;
import elp.edu.pe.horario.domain.repository.*;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioSolucionBuilderImpl implements HorarioSolucionBuilder {

    private final static Logger log = LoggerFactory.getLogger(HorarioSolucionBuilderImpl.class);
    private final AulaRepository aulaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;
    private final RestriccionDocenteRepository restriccionDocenteRepository;

    @Transactional
    @Override
    public HorarioSolucion construirDesdeBaseDeDatos() {
        HorarioSolucion solucion = new HorarioSolucion();

        List<Aula> aulas = aulaRepository.findAll();
        List<BloqueHorario> bloques = bloqueHorarioRepository.findAll();
        List<CursoSeccionDocente> cursoSeccionDocentes = cursoSeccionDocenteRepository.findAll();

        cargarRestriccionesParaDocentes(cursoSeccionDocentes);

        log.info("Cargados: {} aulas, {} bloques, {} curso-seccion-docentes",
                aulas.size(), bloques.size(), cursoSeccionDocentes.size());

        solucion.setAulaList(aulas);
        solucion.setBloqueHorarioList(bloques);
        solucion.setCursoSeccionDocentes(cursoSeccionDocentes);

        // Crear lista vacía de asignaciones horario
        Random random = new Random();
        List<AsignacionHorario> asignacionesIniciales = cursoSeccionDocentes.stream()
                .map(csd -> {
                    AsignacionHorario a = new AsignacionHorario();
                    a.setId(UUID.randomUUID());
                    a.setCursoSeccionDocente(csd);

                    // Asignación aleatoria inicial aleatoria de aula y bloque horario
                    if (!aulas.isEmpty()) {
                        a.setAula(aulas.get(random.nextInt(aulas.size())));
                    }
                    if (!bloques.isEmpty()) {
                        a.setBloqueHorario(bloques.get(random.nextInt(bloques.size())));
                    }

                    return a;
                })
                .toList();

        solucion.setAsignacionHorarioList(asignacionesIniciales);

        log.info("Preparada solución con {} asignaciones iniciales", asignacionesIniciales.size());
        return solucion;
    }

    private void cargarRestriccionesParaDocentes(List<CursoSeccionDocente> cursoSeccionDocentes) {
        Set<Docente> docentesUnicos = cursoSeccionDocentes.stream()
                .map(CursoSeccionDocente::getDocente)
                .collect(Collectors.toSet());

        for (Docente docente : docentesUnicos) {
            List<RestriccionDocente> restricciones = restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            docente.setRestricciones(restricciones);
            log.info("Docente {} tiene {} restricciones cargadas", docente.getNombre(), restricciones.size());
        }
    }
}