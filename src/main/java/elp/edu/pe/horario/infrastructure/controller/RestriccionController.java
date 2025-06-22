package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.RestriccionDocenteDto;
import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.restriccion_docente.CrearRestriccionUsecase;
import elp.edu.pe.horario.application.usecase.restriccion_docente.CrearRestriccionesBatchUseCase;
import elp.edu.pe.horario.application.usecase.restriccion_docente.ObtenerRestriccionesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/restriccion-docente")
@RequiredArgsConstructor
public class RestriccionController {

    private final CrearRestriccionUsecase crearRestriccionUsecase;
    private final ObtenerRestriccionesUseCase obtenerRestricciones;
    private final CrearRestriccionesBatchUseCase crearRestriccionesBatchUseCase;

    @PostMapping
    public ResponseEntity<RegistroResponse> createRestriccion(@RequestBody RestriccionRequest request) {
        RegistroResponse response = crearRestriccionUsecase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch")
    public ResponseEntity<RegistroResponse> createBatchRestricciones(@RequestBody List<RestriccionRequest> requests) {
        RegistroResponse response = crearRestriccionesBatchUseCase.execute(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<RestriccionDocenteDto>> obtenerRestricciones() {
        List<RestriccionDocenteDto> restricciones = obtenerRestricciones.obtenerRestricciones();
        return ResponseEntity.ok(restricciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<RestriccionDocenteDto>> obtenerRestriccionPorId(@PathVariable UUID id) {
        Optional<RestriccionDocenteDto> restriccion = obtenerRestricciones.obtenerRestriccionPorId(id);
        return ResponseEntity.ok(restriccion);
    }

    @GetMapping("/docente/{id}")
    public ResponseEntity<List<RestriccionDocenteDto>> obtenerRestriccionesPorDocente(@PathVariable UUID id) {
        List<RestriccionDocenteDto> restricciones = obtenerRestricciones.obtenerRestriccionesPorDocente(id);
        return ResponseEntity.ok(restricciones);
    }
}
