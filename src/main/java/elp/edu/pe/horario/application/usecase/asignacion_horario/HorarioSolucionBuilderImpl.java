package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.enums.TipoAula;
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
    private final AulaRepository aulaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final CursoSeccionRepository cursoSeccionRepository;
    private final DocenteRepository docenteRepository;
    private final RestriccionDocenteRepository restriccionDocenteRepository;

    public HorarioSolucionBuilderImpl(AulaRepository aulaRepository, BloqueHorarioRepository bloqueHorarioRepository, CursoSeccionRepository cursoSeccionRepository, DocenteRepository docenteRepository, RestriccionDocenteRepository restriccionDocenteRepository) {
        this.aulaRepository = aulaRepository;
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.cursoSeccionRepository = cursoSeccionRepository;
        this.docenteRepository = docenteRepository;
        this.restriccionDocenteRepository = restriccionDocenteRepository;
    }

    @Override
    public HorarioSolucion construirDesdeBaseDeDatos() {
        HorarioSolucion solucion = new HorarioSolucion();
        // Cargar datos desde la base de datos
        solucion.setAulaList(aulaRepository.findAll());
        solucion.setBloqueHorarioList(bloqueHorarioRepository.findAll());
        solucion.setCursoSeccionList(cursoSeccionRepository.findAll());

        List<Docente> docentes = docenteRepository.findAll();

        // Asignar restricciones a los docentes
        for (Docente docente : docentes) {
            List<RestriccionDocente> restricciones = restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            log.info("Docente: {} tiene {} restricciones", docente.getNombre(), restricciones.size());
            docente.setRestricciones(restricciones);
        }

        solucion.setDocenteList(docentes);

        // Generar las asignaciones sin aula ni bloque
        List<AsignacionHorario> asignaciones = generarAsignaciones(
                docentes, solucion.getCursoSeccionList(),
                solucion.getAulaList(),
                solucion.getBloqueHorarioList());

        solucion.setAsignacionHorarioList(asignaciones);

        return solucion;
    }

    public List<AsignacionHorario> generarAsignaciones(List<Docente> docentes, List<CursoSeccion> cursoSecciones, List<Aula> aulas, List<BloqueHorario> bloques) {
        List<AsignacionHorario> asignaciones = new ArrayList<>();

        if (aulas.isEmpty() || bloques.isEmpty()) {
            if (aulas.isEmpty()) {
                log.error("⚠️ No hay aulas disponibles");
            }
            if (bloques.isEmpty()) {
                log.error("⚠️ No hay bloques horarios disponibles");
            }
            return asignaciones;
        }

        for (CursoSeccion cs : cursoSecciones) {
            Curso curso = cs.getCurso();
            UnidadAcademica unidadMateria = curso.getUnidad();

            // Log para verificar las unidades académicas del curso
            log.info("Buscando docentes para curso {} de la unidad {}", curso.getNombre(), unidadMateria.getNombre());

            // Filtrar docentes por unidad académica
            List<Docente> docentesFiltrados = docentes.stream()
                    .filter(d -> d.getUnidadesAcademicas() != null &&
                            d.getUnidadesAcademicas().stream()
                                    .anyMatch(u -> u.getId().equals(unidadMateria.getId())))
                    .toList();

            if (docentesFiltrados.isEmpty()) {
                log.error("⚠️ No se encontró docente para {} (Unidad: {})", curso.getNombre(), unidadMateria.getNombre());
                continue;
            }

            // Seleccionamos el primer docente disponible
            Docente docente = docentesFiltrados.get(0);

            // Asignamos el aula adecuada según el tipo de curso
            Aula aulaInicial = aulas.stream()
                    .filter(a -> a.getTipo() == TipoAula.valueOf(curso.getTipo().toUpperCase()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("No se encontró aula para el tipo de curso: " + curso.getTipo()));

            // Tomamos el primer bloque disponible
            BloqueHorario bloqueInicial = bloques.get(0);

            // Creamos la asignación con valores iniciales
            AsignacionHorario asignacion = new AsignacionHorario();

            // Verificamos si el ID de la asignación es nulo y lo generamos si es necesario
            if (asignacion.getId() == null) {
                asignacion.setId(UUID.randomUUID()); // Asignar un UUID único para OptaPlanner
            }

            // Asignamos el aula y bloque horario a la asignación
            if (aulaInicial.getId() == null) {
                aulaInicial.setId(UUID.randomUUID());  // Asignamos un ID único al aula si no tiene
            }

            asignacion.setDocente(docente);
            asignacion.setCursoSeccion(cs);
            asignacion.setAula(aulaInicial);
            asignacion.setBloqueHorario(bloqueInicial);

            // Añadimos la asignación a la lista
            asignaciones.add(asignacion);

            // Log para verificar la asignación creada
            log.info("Asignación creada: Docente={}, Curso={}, Aula={}, Bloque={}",
                    docente.getNombre(),
                    curso.getNombre(),
                    aulaInicial.getNombre(),
                    bloqueInicial.getDiaSemana() + " " + bloqueInicial.getHoraInicio());
        }

        // Devolvemos la lista de asignaciones
        return asignaciones;
    }
}
