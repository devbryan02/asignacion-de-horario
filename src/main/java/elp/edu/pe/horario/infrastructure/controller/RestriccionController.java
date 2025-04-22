package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.restriccion_docente.CrearRestriccionUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restriccion-docente")
public class RestriccionController {

    private final CrearRestriccionUsecase crearRestriccionUsecase;

    public RestriccionController(CrearRestriccionUsecase crearRestriccionUsecase) {
        this.crearRestriccionUsecase = crearRestriccionUsecase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> createRestriccion(@RequestBody RestriccionRequest request) {
        RegistroResponse response = crearRestriccionUsecase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

}
