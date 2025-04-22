package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.usecase.asignacion_horario.HorarioSolverUseCase;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/horarios")
public class HorarioSolverController {

    private final HorarioSolverUseCase horarioSolverUseCase;
    private final HorarioSolucionBuilder  horarioSolucionBuilder;

    public HorarioSolverController(HorarioSolverUseCase horarioSolverUseCase, HorarioSolucionBuilder horarioSolucionBuilder) {
        this.horarioSolverUseCase = horarioSolverUseCase;
        this.horarioSolucionBuilder = horarioSolucionBuilder;
    }

    @PostMapping("/resolver")
    public ResponseEntity<HorarioSolucion> resolverHorario() {
        try {
            HorarioSolucion problema = horarioSolucionBuilder.construirDesdeBaseDeDatos();
            HorarioSolucion solucion = horarioSolverUseCase.ejecutar(problema);
            return ResponseEntity.ok(solucion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
