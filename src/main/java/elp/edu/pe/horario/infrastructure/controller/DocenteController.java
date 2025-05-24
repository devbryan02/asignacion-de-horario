package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.DocenteDto;
import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.docente.ActualizarDocenteUseCase;
import elp.edu.pe.horario.application.usecase.docente.CrearDocenteUseCase;
import elp.edu.pe.horario.application.usecase.docente.EliminarDocenteUseCase;
import elp.edu.pe.horario.application.usecase.docente.ObtenerDocentesUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    private final CrearDocenteUseCase crearDocenteUseCase;
    private final ObtenerDocentesUseCase obtenerDocentesUseCase;
    private final ActualizarDocenteUseCase actualizarDocenteUseCase;
    private final EliminarDocenteUseCase eliminarDocenteUseCase;

    public DocenteController(CrearDocenteUseCase crearDocenteUseCase,
                             ObtenerDocentesUseCase obtenerDocentesUseCase,
                             ActualizarDocenteUseCase actualizarDocenteUseCase,
                             EliminarDocenteUseCase eliminarDocenteUseCase) {
        this.crearDocenteUseCase = crearDocenteUseCase;
        this.obtenerDocentesUseCase = obtenerDocentesUseCase;
        this.actualizarDocenteUseCase = actualizarDocenteUseCase;
        this.eliminarDocenteUseCase = eliminarDocenteUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearDocente(@RequestBody DocenteRequest request) {
        RegistroResponse response = crearDocenteUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DocenteDto>> obtenerDocentes() {
        List<DocenteDto> docentes = obtenerDocentesUseCase.obtenerDocentes();
        return ResponseEntity.ok(docentes);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponse> actualizarDocente(@PathVariable UUID id, @RequestBody DocenteRequest request) {
        RegistroResponse response = actualizarDocenteUseCase.ejecutar(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body( response );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarDocente(@PathVariable UUID id) {
        eliminarDocenteUseCase.ejecutar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Docente eliminado correctamente");
    }
}
