package elp.edu.pe.horario.application.usecase.asignacion_horario;

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
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@Transactional
public class HorarioSolverUseCase {

    private final SolverManager<HorarioSolucion, UUID> solverManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final Logger log = LoggerFactory.getLogger(HorarioSolverUseCase.class);
    private EntityManager entityManager;

    public HorarioSolverUseCase(SolverManager<HorarioSolucion, UUID> solverManager,
                                 AsignacionHorarioRepository asignacionHorarioRepository,
                                 EntityManager entityManager) {
        this.solverManager = solverManager;
        this.asignacionHorarioRepository = asignacionHorarioRepository;
        this.entityManager = entityManager;
    }

    public HorarioSolucion ejecutar(HorarioSolucion solucion) throws ExecutionException, InterruptedException {

        try{
            UUID solucionId = UUID.randomUUID();
            SolverJob<HorarioSolucion, UUID> solverJob = solverManager.solve(solucionId, solucion);
            HorarioSolucion solucionFinal = solverJob.getFinalBestSolution();

            if(solucionFinal == null){
                throw new IllegalStateException("No se pudo resolver el horario");
            }

            entityManager.clear();

            asignacionHorarioRepository.deleteAllInBatch();

            List<AsignacionHorario> nuevasAsignaciones = solucionFinal.getAsignacionHorarioList();
            nuevasAsignaciones.forEach(asignacion -> {
                asignacion.setId(null);
                asignacionHorarioRepository.save(asignacion);
            });
            return solucionFinal;
        }catch (Exception e){
            log.error("Error resolviendo horario", e);
            throw new RuntimeException("Error resolviendo horario", e);
        }

    }
}
