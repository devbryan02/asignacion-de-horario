package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.BloqueRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.bloque_horario.CrearBloqueUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bloque-horario")
public class BloqueController {

    private final CrearBloqueUseCase crearBloqueUseCase;

    public BloqueController(CrearBloqueUseCase crearBloqueUseCase) {
        this.crearBloqueUseCase = crearBloqueUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearBloque(@RequestBody BloqueRequest request){
        RegistroResponse response = crearBloqueUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
