package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.UnidadDto;
import elp.edu.pe.horario.application.dto.request.UnidadRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.unidad.CrearUnidadUseCase;
import elp.edu.pe.horario.application.usecase.unidad.ObtenerUnidadesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/unidad-academica")
public class UnidadController {

    private final CrearUnidadUseCase crearUnidadUseCase;
    private final ObtenerUnidadesUseCase obtenerUnidadesUseCase;

    public UnidadController(CrearUnidadUseCase crearUnidadUseCase, ObtenerUnidadesUseCase obtenerUnidadesUseCase) {
        this.crearUnidadUseCase = crearUnidadUseCase;
        this.obtenerUnidadesUseCase = obtenerUnidadesUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearUnidad(@RequestBody UnidadRequest request) {
        RegistroResponse response = crearUnidadUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UnidadDto>> obtenerUnidades() {
        List<UnidadDto> unidades = obtenerUnidadesUseCase.obtenerUnidades();
        return ResponseEntity.ok(unidades);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UnidadDto>> obtenerUnidadPorId(@PathVariable UUID id) {
        var unidad = obtenerUnidadesUseCase.obtenerUnidadPorId(id);
        return ResponseEntity.ok(unidad);
    }

}
