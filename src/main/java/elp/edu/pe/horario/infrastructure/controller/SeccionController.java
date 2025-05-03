package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.SeccionDto;
import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.seccion.CrearSeccionUseCase;
import elp.edu.pe.horario.application.usecase.seccion.ObtenerSeccionesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/seccion-academica")
public class SeccionController {

    private final CrearSeccionUseCase crearSeccionUseCase;
    private final ObtenerSeccionesUseCase obtenerSeccionesUseCase;

    public SeccionController(CrearSeccionUseCase crearSeccionUseCase, ObtenerSeccionesUseCase obtenerSeccionesUseCase) {
        this.crearSeccionUseCase = crearSeccionUseCase;
        this.obtenerSeccionesUseCase = obtenerSeccionesUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearSeccion(@RequestBody SeccionRequest request) {
        RegistroResponse response = crearSeccionUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SeccionDto>> obtenerSecciones() {
        List<SeccionDto> secciones = obtenerSeccionesUseCase.obtenerSecciones();
        return ResponseEntity.ok(secciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SeccionDto>> obtenerSeccionPorId(@PathVariable UUID id) {
        Optional<SeccionDto> seccion = obtenerSeccionesUseCase.obtenerSeccionPorId(id);
        return ResponseEntity.ok(seccion);
    }
}
