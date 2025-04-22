package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.UnidadRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.unidad.CrearUnidadUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unidad-academica")
public class UnidadController {

    private final CrearUnidadUseCase crearUnidadUseCase;

    public UnidadController(CrearUnidadUseCase crearUnidadUseCase) {
        this.crearUnidadUseCase = crearUnidadUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearUnidad(@RequestBody UnidadRequest request) {
        RegistroResponse response = crearUnidadUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

}
