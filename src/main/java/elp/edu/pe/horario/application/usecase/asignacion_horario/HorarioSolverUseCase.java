package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.application.dto.response.GeneracionHorarioResponse;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
public class HorarioSolverUseCase {

    private final SolverManager<HorarioSolucion, UUID> solverManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final Logger log = LoggerFactory.getLogger(HorarioSolverUseCase.class);
    private final EntityManager entityManager;

    public HorarioSolverUseCase(SolverManager<HorarioSolucion, UUID> solverManager,
                                 AsignacionHorarioRepository asignacionHorarioRepository,
                                 EntityManager entityManager) {
        this.solverManager = solverManager;
        this.asignacionHorarioRepository = asignacionHorarioRepository;
        this.entityManager = entityManager;
    }

    public GeneracionHorarioResponse ejecutar(HorarioSolucion solucion) throws ExecutionException, InterruptedException {

        try {
            UUID solucionId = UUID.randomUUID();
            SolverJob<HorarioSolucion, UUID> solverJob = solverManager.solve(solucionId, solucion);
            HorarioSolucion solucionFinal = solverJob.getFinalBestSolution();

            if (solucionFinal == null) {
                throw new IllegalStateException("No se pudo resolver el horario");
            }

            entityManager.clear();
            asignacionHorarioRepository.deleteAllInBatch();

            List<AsignacionHorario> nuevasAsignaciones = solucionFinal.getAsignacionHorarioList();
            nuevasAsignaciones.forEach(asignacion -> {
                asignacion.setId(null);
                asignacionHorarioRepository.save(asignacion);
            });

            int cantidadAsignaciones = nuevasAsignaciones.size();

            Set<UUID> aulasUsadas = nuevasAsignaciones.stream()
                    .map(a -> a.getAula().getId()) // o getNombre()
                    .collect(Collectors.toSet());

            Set<String> bloquesUsados = nuevasAsignaciones.stream()
                    .map(a -> a.getBloqueHorario().toString()) // simplificado
                    .collect(Collectors.toSet());

            // Docentes asignados
            Map<String, List<AsignacionHorario>> asignacionesPorDocente = nuevasAsignaciones.stream()
                    .collect(Collectors.groupingBy(a -> String.valueOf(a.getDocente().getId())));

            int cantidadDocentesAsignados = asignacionesPorDocente.size();

            // Promedio de cursos y aulas por docente
            double promedioCursosPorDocente = asignacionesPorDocente.values().stream()
                    .mapToInt(list -> (int) list.stream().map(AsignacionHorario::getCursoSeccion).distinct().count())
                    .average().orElse(0.0);

            double promedioAulasPorDocente = asignacionesPorDocente.values().stream()
                    .mapToInt(list -> (int) list.stream().map(AsignacionHorario::getAula).distinct().count())
                    .average().orElse(0.0);

            // Score del solucionador
            long hardScore = solucionFinal.getScore().hardScore();
            long softScore = solucionFinal.getScore().softScore();

            String calidadGeneracion;
            String mensajeEvaluacion;

            if (hardScore < 0) {
                calidadGeneracion = "Deficiente";
                mensajeEvaluacion = "Se han violado restricciones importantes. Revisa la configuración de cursos, aulas o docentes.";
            } else if (softScore < -20) {
                calidadGeneracion = "Aceptable";
                mensajeEvaluacion = "Se cumplió con lo esencial, pero hay varias preferencias no respetadas.";
            } else if (softScore < 0) {
                calidadGeneracion = "Buena";
                mensajeEvaluacion = "Buena solución. Algunas preferencias fueron ignoradas, pero no hay conflictos graves.";
            } else {
                calidadGeneracion = "Excelente";
                mensajeEvaluacion = "¡Horario óptimo generado sin conflictos ni preferencias ignoradas!";
            }

            return new GeneracionHorarioResponse(
                    "Horario generado exitosamente",
                    cantidadAsignaciones,
                    aulasUsadas.size(),
                    bloquesUsados.size(),
                    cantidadDocentesAsignados,
                    calidadGeneracion,
                    mensajeEvaluacion
            );

        } catch (Exception e) {
            log.error("Error resolviendo horario", e);
            throw new RuntimeException("Error resolviendo horario", e);
        }
    }

}
