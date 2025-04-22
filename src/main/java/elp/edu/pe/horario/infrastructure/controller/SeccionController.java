package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.seccion.CrearSeccionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seccion-academica")
public class SeccionController {

    private final CrearSeccionUseCase crearSeccionUseCase;

    public SeccionController(CrearSeccionUseCase crearSeccionUseCase) {
        this.crearSeccionUseCase = crearSeccionUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearSeccion(@RequestBody SeccionRequest request) {
        RegistroResponse response = crearSeccionUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

}
