package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class HorarioSolverUseCase {

    private final SolverManager<HorarioSolucion, UUID> solverManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;

    public HorarioSolverUseCase(SolverManager<HorarioSolucion, UUID> solverManager, AsignacionHorarioRepository asignacionHorarioRepository) {
        this.solverManager = solverManager;
        this.asignacionHorarioRepository = asignacionHorarioRepository;
    }

    public HorarioSolucion ejecutar(HorarioSolucion problema) throws ExecutionException, InterruptedException {

        UUID problemaId = UUID.randomUUID();
        SolverJob<HorarioSolucion, UUID> solverJob = solverManager.solve(problemaId, problema);
        HorarioSolucion solucion = solverJob.getFinalBestSolution();

        List<AsignacionHorario> asignaciones = solucion.getAsignacionHorarioList();

        asignacionHorarioRepository.saveAll(asignaciones);
        return solucion;

    }

}
