package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.PeriodoDto;
import elp.edu.pe.horario.application.dto.request.PeriodoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.usecase.periodo.ActualizarPeriodoUsecase;
import elp.edu.pe.horario.application.usecase.periodo.CrearPeriodoUseCase;
import elp.edu.pe.horario.application.usecase.periodo.EliminarPeriodoUseCase;
import elp.edu.pe.horario.application.usecase.periodo.ObtenerPeriodosUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/periodo-academico")
@RequiredArgsConstructor
public class PeriodoController {

    private final CrearPeriodoUseCase crearPeriodoUseCase;
    private final ObtenerPeriodosUsecase obtenerPeriodos;
    private final EliminarPeriodoUseCase eliminarPeriodoUseCase;
    private final ActualizarPeriodoUsecase actualizarPeriodoUsecase;

    @PostMapping
    public ResponseEntity<RegistroResponse> crearPeriodo(@RequestBody PeriodoRequest request){
        RegistroResponse response = crearPeriodoUseCase.ejecutar(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PeriodoDto>> obtenerPeriodos(){
        List<PeriodoDto> periodos = obtenerPeriodos.obtenerPeriodos();
        return ResponseEntity.ok(periodos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PeriodoDto>> obtenerPeriodoPorId(@PathVariable UUID id){
        Optional<PeriodoDto> periodo = obtenerPeriodos.obtenerPeriodoPorId(id);
        return ResponseEntity.ok(periodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPeriodo(@PathVariable UUID id){
        eliminarPeriodoUseCase.ejecutar(id);
        return ResponseEntity.ok("Periodo eliminado correctamente");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RegistroResponse> actualizarPeriodo(@RequestBody PeriodoRequest request, @PathVariable UUID id) {
        RegistroResponse response = actualizarPeriodoUsecase.ejecutar(request, id);
        return ResponseEntity.ok(response);
    }
}
