package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.SeccionDto;
import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.seccion.ActualizarSeccionUseCase;
import elp.edu.pe.horario.application.usecase.seccion.CrearSeccionUseCase;
import elp.edu.pe.horario.application.usecase.seccion.EliminarSeccionUseCase;
import elp.edu.pe.horario.application.usecase.seccion.ObtenerSeccionesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/seccion-academica")
@RequiredArgsConstructor
public class SeccionController {

    private final CrearSeccionUseCase crearSeccionUseCase;
    private final ObtenerSeccionesUseCase obtenerSeccionesUseCase;
    private final ActualizarSeccionUseCase actualizarSeccionUseCase;
    private final EliminarSeccionUseCase eliminarSeccionUseCase;

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

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponse> actualizarSeccion(@PathVariable UUID id, @RequestBody SeccionRequest request) {
        RegistroResponse response = actualizarSeccionUseCase.ejecutar(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarSeccion(@PathVariable UUID id) {
         eliminarSeccionUseCase.ejecutar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body( "Seccion eliminada correctamente");
    }
}
