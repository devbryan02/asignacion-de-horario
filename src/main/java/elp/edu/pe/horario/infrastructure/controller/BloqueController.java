package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.BloqueDto;
import elp.edu.pe.horario.application.dto.request.BloqueRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.bloque_horario.ActualizarBloqueUsecase;
import elp.edu.pe.horario.application.usecase.bloque_horario.CrearBloqueUseCase;
import elp.edu.pe.horario.application.usecase.bloque_horario.EliminarBloqueUseCase;
import elp.edu.pe.horario.application.usecase.bloque_horario.ObtenerBloquesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bloque-horario")
@RequiredArgsConstructor
public class BloqueController {

    private final CrearBloqueUseCase crearBloqueUseCase;
    private final ObtenerBloquesUseCase obtenerBloquesUseCase;
    private final ActualizarBloqueUsecase actualizarBloqueUsecase;
    private final EliminarBloqueUseCase eliminarBloqueUseCase;

    @PostMapping
    public ResponseEntity<RegistroResponse> crearBloque(@RequestBody BloqueRequest request){
        RegistroResponse response = crearBloqueUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BloqueDto>> obtenerBloques(){
        List<BloqueDto> bloques = obtenerBloquesUseCase.obtenerBloques();
        return ResponseEntity.ok(bloques);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<BloqueDto>> obtenerBloquePorId(@PathVariable UUID id){
        Optional<BloqueDto> bloque = obtenerBloquesUseCase.obtenerBloquePorId(id);
        return ResponseEntity.ok(bloque);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponse> actualizarBloque(@PathVariable UUID id, @RequestBody BloqueRequest request){
        RegistroResponse response = actualizarBloqueUsecase.ejecutar(request, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarBloque(@PathVariable UUID id){
        this.eliminarBloqueUseCase.ejecutar(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bloque eliminado correctamente");
    }

}
