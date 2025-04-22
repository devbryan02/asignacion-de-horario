package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.CursoSeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.curso_seccion.CrearCursoSeccionUsecase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso-seccion")
public class CursoSeccionController {

    private final CrearCursoSeccionUsecase crearCursoSeccionUsecase;

    public CursoSeccionController(CrearCursoSeccionUsecase crearCursoSeccionUsecase) {
        this.crearCursoSeccionUsecase = crearCursoSeccionUsecase;
    }

    public ResponseEntity<RegistroResponse> crearCursoSeccion(@RequestBody CursoSeccionRequest request) {
        RegistroResponse response = crearCursoSeccionUsecase.ejecutar(request);
        return ResponseEntity.ok(response);
    }
}
