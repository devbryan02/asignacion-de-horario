package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.PeriodoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.periodo.CrearPeriodoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/periodo-academico")
public class PeriodoController {

    private final CrearPeriodoUseCase crearPeriodoUseCase;

    public PeriodoController(CrearPeriodoUseCase crearPeriodoUseCase) {
        this.crearPeriodoUseCase = crearPeriodoUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearPeriodo(@RequestBody PeriodoRequest request){
        RegistroResponse response = crearPeriodoUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
