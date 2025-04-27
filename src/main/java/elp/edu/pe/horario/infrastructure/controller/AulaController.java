package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.AulaDto;
import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.asignacion_horario.ObtenerHorariosUseCase;
import elp.edu.pe.horario.application.usecase.aula.CrearAulaUsecase;
import elp.edu.pe.horario.application.usecase.aula.ObtenerAulasUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/aula")
public class AulaController {

    private final CrearAulaUsecase crearAulaUsecase;
    private final ObtenerAulasUseCase obtenerAulasUseCase;

    public AulaController(CrearAulaUsecase crearAulaUsecase, ObtenerAulasUseCase obtenerAulasUseCase) {
        this.crearAulaUsecase = crearAulaUsecase;
        this.obtenerAulasUseCase = obtenerAulasUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> createAula(@RequestBody AulaRequest request) {
        RegistroResponse response = crearAulaUsecase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AulaDto>> ObtenerAulas() {
        List<AulaDto> aulas = obtenerAulasUseCase.obtenerAulas();
        return ResponseEntity.ok(aulas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AulaDto>> ObtenerAula(@PathVariable UUID id) {
        Optional<AulaDto> aula = obtenerAulasUseCase.obtenerAulaPorId(id);
        return ResponseEntity.ok(aula);
    }

}
