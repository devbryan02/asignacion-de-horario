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

        // Asegurar distribución mínima de 3 asignaciones por bloque
        int bloquesNecesarios = (int) Math.ceil(cursoSeccionDocentes.size() / 3.0);
        if (bloques.size() < bloquesNecesarios) {
            throw new IllegalStateException(
                    String.format("Se necesitan al menos %d bloques para una distribución óptima. Actuales: %d",
                            bloquesNecesarios, bloques.size())
            );
        }

        // Verificar capacidad mínima de aulas
        int aulasNecesarias = (int) Math.ceil((double) cursoSeccionDocentes.size() / bloques.size());
        if (aulas.size() < aulasNecesarias) {
            throw new IllegalStateException(
                    String.format("Se necesitan al menos %d aulas para evitar superposiciones. Actuales: %d",
                            aulasNecesarias, aulas.size())
            );
        }

        cargarRestriccionesParaDocentes(cursoSeccionDocentes);

        log.info("Configuración inicial -> Aulas: {}, Bloques: {}, Asignaciones: {}, " +
                        "Bloques mínimos necesarios: {}, Aulas mínimas necesarias: {}",
                aulas.size(), bloques.size(), cursoSeccionDocentes.size(),
                bloquesNecesarios, aulasNecesarias);

        // Distribuir asignaciones iniciales en bloques diferentes
        List<AsignacionHorario> asignacionesIniciales = crearAsignacionesDistribuidas(
                cursoSeccionDocentes, bloques, aulas);

        solucion.setAulaList(aulas);
        solucion.setBloqueHorarioList(bloques);
        solucion.setCursoSeccionDocentes(cursoSeccionDocentes);
        solucion.setAsignacionHorarioList(asignacionesIniciales);

        return solucion;
    }

    private List<AsignacionHorario> crearAsignacionesDistribuidas(
            List<CursoSeccionDocente> cursoSeccionDocentes,
            List<BloqueHorario> bloques,
            List<Aula> aulas) {

        List<AsignacionHorario> asignaciones = new ArrayList<>();
        int totalAsignaciones = cursoSeccionDocentes.size();
        int asignacionesPorBloque = (int) Math.ceil((double) totalAsignaciones / bloques.size());

        for (int i = 0; i < cursoSeccionDocentes.size(); i++) {
            CursoSeccionDocente csd = cursoSeccionDocentes.get(i);

            // Distribuir en diferentes bloques
            int bloqueIndex = i % bloques.size();
            int aulaIndex = (i / bloques.size()) % aulas.size();

            AsignacionHorario asignacion = new AsignacionHorario(
                    UUID.randomUUID(),
                    csd,
                    aulas.get(aulaIndex),
                    bloques.get(bloqueIndex)
            );
            asignaciones.add(asignacion);
        }

        log.info("Creadas {} asignaciones iniciales distribuidas en {} bloques con {} aulas",
                asignaciones.size(), bloques.size(), aulas.size());

        return asignaciones;
    }

    private void cargarRestriccionesParaDocentes(List<CursoSeccionDocente> cursoSeccionDocentes) {
        Set<Docente> docentesUnicos = cursoSeccionDocentes.stream()
                .map(CursoSeccionDocente::getDocente)
                .collect(Collectors.toSet());

        for (Docente docente : docentesUnicos) {
            List<RestriccionDocente> restricciones =
                    restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            docente.setRestricciones(restricciones);
        }
    }
}