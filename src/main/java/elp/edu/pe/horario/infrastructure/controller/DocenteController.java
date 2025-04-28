package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.DocenteDto;
import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.docente.CrearDocenteUseCase;
import elp.edu.pe.horario.application.usecase.docente.ObtenerDocentesUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    private final CrearDocenteUseCase crearDocenteUseCase;
    private final ObtenerDocentesUseCase obtenerDocentesUseCase;

    public DocenteController(CrearDocenteUseCase crearDocenteUseCase, ObtenerDocentesUseCase obtenerDocentesUseCase) {
        this.crearDocenteUseCase = crearDocenteUseCase;
        this.obtenerDocentesUseCase = obtenerDocentesUseCase;
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
}
