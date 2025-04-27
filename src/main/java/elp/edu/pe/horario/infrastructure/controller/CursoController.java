package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.CursoDto;
import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.curso.CrearCursoUseCase;
import elp.edu.pe.horario.application.usecase.curso.ObtenerCursosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/curso")
public class CursoController {

    private final CrearCursoUseCase crearCursoUseCase;
    private final ObtenerCursosUseCase obtenerCursosUseCase;

    public CursoController(CrearCursoUseCase crearCursoUseCase, ObtenerCursosUseCase obtenerCursosUseCase) {
        this.crearCursoUseCase = crearCursoUseCase;
        this.obtenerCursosUseCase = obtenerCursosUseCase;
    }

    @PostMapping
    public ResponseEntity<RegistroResponse> crearCurso(@RequestBody CursoRequest request){
        RegistroResponse response = crearCursoUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> obtenerCursos(){
        List<CursoDto> cursos = obtenerCursosUseCase.obtenerCursos();
        return ResponseEntity.ok(cursos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CursoDto>> obtenerCursoPorId(@PathVariable UUID id){
        Optional<CursoDto> curso = obtenerCursosUseCase.obtenerCursoPorId(id);
        return ResponseEntity.ok(curso);
    }
}
