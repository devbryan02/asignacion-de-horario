package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.aula.CrearAulaUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aula")
public class AulaController {

    private final CrearAulaUsecase crearAulaUsecase;

    public AulaController(CrearAulaUsecase crearAulaUsecase) {
        this.crearAulaUsecase = crearAulaUsecase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> createAula(@RequestBody AulaRequest request) {
        RegistroResponse response = crearAulaUsecase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

}
