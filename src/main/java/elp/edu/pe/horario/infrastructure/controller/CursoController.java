package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.curso.CrearCursoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CrearCursoUseCase crearCursoUseCase;

    public CursoController(CrearCursoUseCase crearCursoUseCase) {
        this.crearCursoUseCase = crearCursoUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearCurso(@RequestBody CursoRequest request){
        RegistroResponse response = crearCursoUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
