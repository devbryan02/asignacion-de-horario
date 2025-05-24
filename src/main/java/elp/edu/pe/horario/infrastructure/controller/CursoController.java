package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.CursoDto;
import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.curso.ActualizarCursoUseCase;
import elp.edu.pe.horario.application.usecase.curso.CrearCursoUseCase;
import elp.edu.pe.horario.application.usecase.curso.EliminarCursoUseCase;
import elp.edu.pe.horario.application.usecase.curso.ObtenerCursosUseCase;
import org.springframework.http.HttpStatus;
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
    private final ActualizarCursoUseCase actualizarCursoUseCase;
    private final EliminarCursoUseCase eliminarCursoUseCase;

    public CursoController(CrearCursoUseCase crearCursoUseCase, ObtenerCursosUseCase obtenerCursosUseCase,
                           ActualizarCursoUseCase actualizarCursoUseCase, EliminarCursoUseCase eliminarCursoUseCase) {
        this.crearCursoUseCase = crearCursoUseCase;
        this.obtenerCursosUseCase = obtenerCursosUseCase;
        this.actualizarCursoUseCase = actualizarCursoUseCase;
        this.eliminarCursoUseCase = eliminarCursoUseCase;
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

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponse> actualizarCurso(@PathVariable UUID id, @RequestBody CursoRequest request){
        RegistroResponse response = actualizarCursoUseCase.ejecutar(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCurso(@PathVariable UUID id){
        eliminarCursoUseCase.ejecutar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Curso eliminado correctamente");
    }
}
