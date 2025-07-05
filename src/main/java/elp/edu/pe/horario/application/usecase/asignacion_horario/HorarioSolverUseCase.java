package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.application.dto.response.GeneracionHorarioResponse;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
@RequiredArgsConstructor
public class HorarioSolverUseCase {

    private final SolverManager<HorarioSolucion, UUID> solverManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final Logger log = LoggerFactory.getLogger(HorarioSolverUseCase.class);
    private final MensajeGeneracionUseCase mostrarMensajeGeneracionUseCase;
    private final AnalizarSolucionUseCase analizarSolucionHorarioUseCase;

    @Transactional
    public GeneracionHorarioResponse ejecutar(HorarioSolucion solucion) throws ExecutionException, InterruptedException {

        try {
            UUID solucionId = UUID.randomUUID();
            SolverJob<HorarioSolucion, UUID> solverJob = solverManager.solve(solucionId, solucion);
            HorarioSolucion solucionFinal = solverJob.getFinalBestSolution();

            if (solucionFinal == null) {
                throw new IllegalStateException("No se pudo resolver el horario");
            }

            List<AsignacionHorario> nuevasAsignaciones = solucionFinal.getAsignacionHorarioList();
            nuevasAsignaciones.forEach(asignacion -> {
                asignacion.setId(null);
                asignacionHorarioRepository.save(asignacion);
            });

            int cantidadAsignaciones = analizarSolucionHorarioUseCase.obtenerCantidadAsignaciones(nuevasAsignaciones);
            int cantidadAulasUsadas = analizarSolucionHorarioUseCase.obtenerAulasUsadas(nuevasAsignaciones).size();
            int cantidadBloquesUsados = analizarSolucionHorarioUseCase.obtenerBloquesUsados(nuevasAsignaciones).size();
            int cantidadDocentesAsignados = analizarSolucionHorarioUseCase.obtenerCantidadDocentesAsignados(nuevasAsignaciones);

            long hardScore = solucionFinal.getScore().hardScore();
            long softScore = solucionFinal.getScore().softScore();

            String calidadGeneracion = mostrarMensajeGeneracionUseCase.determinarCalidadGeneracion(hardScore, softScore);
            String mensajeEvaluacion = mostrarMensajeGeneracionUseCase.generarMensajeEvaluacion(hardScore, softScore);

            return new GeneracionHorarioResponse(
                    "Horario generado exitosamente",
                    cantidadAsignaciones,
                    cantidadAulasUsadas,
                    cantidadBloquesUsados,
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