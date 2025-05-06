package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.request.CursoSeccionBulkRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.curso_seccion.CrearCursoSeccionBulkUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/curso-seccion")
public class CursoSeccionController {

    private final CrearCursoSeccionBulkUseCase bulkUseCase;

    public CursoSeccionController(CrearCursoSeccionBulkUseCase bulkUseCase) {
        this.bulkUseCase = bulkUseCase;
    }

    @PostMapping("/bulk")
    public ResponseEntity<RegistroResponse> registrarBulk(@RequestBody CursoSeccionBulkRequest request) {
        RegistroResponse response = bulkUseCase.ejecutar(request);
        return ResponseEntity.status(response.success() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(response);
    }

}
