package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.response.GeneracionHorarioResponse;
import elp.edu.pe.horario.application.usecase.asignacion_horario.HorarioSolverUseCase;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/horario")
@RequiredArgsConstructor
public class HorarioSolverController {

    private final HorarioSolverUseCase horarioSolverUseCase;
    private final HorarioSolucionBuilder  horarioSolucionBuilder;
    private final static Logger log = LoggerFactory.getLogger(HorarioSolverController.class);

    @PostMapping("/resolver")
    public ResponseEntity<?> resolverHorario(@RequestParam UUID periodoId) {
        try {
            // Construimos el problema inicial
            HorarioSolucion problema = horarioSolucionBuilder.construirDesdeBaseDeDatos(periodoId);
            // Intentamos resolver
            GeneracionHorarioResponse solucion = horarioSolverUseCase.ejecutar(problema);
            return ResponseEntity.ok(solucion);

        } catch (Exception e) {
            log.error("Error al resolver el horario: ", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}







