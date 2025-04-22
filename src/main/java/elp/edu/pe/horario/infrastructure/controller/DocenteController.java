package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.docente.CrearDocenteUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    private final CrearDocenteUseCase crearDocenteUseCase;

    public DocenteController(CrearDocenteUseCase crearDocenteUseCase) {
        this.crearDocenteUseCase = crearDocenteUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearDocente(@RequestBody DocenteRequest request) {
        RegistroResponse response = crearDocenteUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

}
