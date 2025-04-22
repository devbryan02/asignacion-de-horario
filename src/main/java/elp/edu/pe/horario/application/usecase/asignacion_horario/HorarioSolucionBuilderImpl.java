package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.model.*;
import elp.edu.pe.horario.domain.repository.*;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HorarioSolucionBuilderImpl implements HorarioSolucionBuilder {

    private final static Logger log = LoggerFactory.getLogger(HorarioSolucionBuilderImpl.class);
    private final AulaRepository  aulaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final CursoSeccionRepository cursoSeccionRepository;
    private final DocenteRepository docenteRepository;
    private final RestriccionDocenteRepository restriccionDocenteRepository;

    public HorarioSolucionBuilderImpl(AulaRepository aulaRepository, BloqueHorarioRepository bloqueHorarioRepository, AsignacionHorarioRepository asignacionHorarioRepository, CursoSeccionRepository cursoSeccionRepository, DocenteRepository docenteRepository, RestriccionDocenteRepository restriccionDocenteRepository) {
        this.aulaRepository = aulaRepository;
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.asignacionHorarioRepository = asignacionHorarioRepository;
        this.cursoSeccionRepository = cursoSeccionRepository;
        this.docenteRepository = docenteRepository;
        this.restriccionDocenteRepository = restriccionDocenteRepository;
    }

    @Override
    public HorarioSolucion construirDesdeBaseDeDatos(){

        HorarioSolucion solucion = new HorarioSolucion();
        solucion.setAulaList(aulaRepository.findAll());
        solucion.setBloqueHorarioList(bloqueHorarioRepository.findAll());
        solucion.setCursoSeccionList(cursoSeccionRepository.findAll());

        List<Docente> docentes = docenteRepository.findAll();

        // asignamos restricciones al docente
        for (Docente docente : docentes) {
            List<RestriccionDocente> restricciones = restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            docente.setRestricciones(restricciones);
        }

        solucion.setDocenteList(docentes);

        //Generamos las asignaciones sin aula ni bloque
        List<AsignacionHorario> asignaciones = generarAsignaciones(docentes, solucion.getCursoSeccionList());

        solucion.setAsignacionHorarioList(asignaciones);

        return solucion;
    }

    public List<AsignacionHorario> generarAsignaciones(List<Docente> docentes, List<CursoSeccion> cursoSecciones) {
        List<AsignacionHorario> asignaciones = new ArrayList<>();

        for (CursoSeccion cs : cursoSecciones) {
            Curso curso = cs.getCurso();
            UnidadAcademica unidadMateria = curso.getUnidad();

            // Filtrar docentes de la misma unidad académica
            List<Docente> docentesFiltrados = docentes.stream()
                    .filter(d -> d.getUnidadesAcademicas().contains(unidadMateria))
                    .toList();

            if (docentesFiltrados.isEmpty()) {
                log.error("⚠️ No se encontró docente para {}", curso.getNombre());
                continue;
            }

            // Por ahora, le asignamos el primero (puedes mejorar esto con balanceo u horas)
            Docente docente = docentesFiltrados.get(0);

            asignaciones.add(new AsignacionHorario(
                    UUID.randomUUID(),
                    docente,
                    cs,
                    null,
                    null
            ));
        }
        return asignaciones;
    }

}
