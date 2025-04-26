package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.dto.HorarioDto;
import elp.edu.pe.horario.application.usecase.asignacion_horario.ObtenerHorariosUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/horarios")
public class HorarioController {

    private final ObtenerHorariosUseCase obtenerHorariosUseCase;

    public HorarioController(ObtenerHorariosUseCase obtenerHorariosUseCase) {
        this.obtenerHorariosUseCase = obtenerHorariosUseCase;
    }

    @GetMapping("/seccion/{seccionID}")
    public ResponseEntity<List<HorarioDto>> obtenerHorariosPorSeccion(@PathVariable UUID seccionID) {
        List<HorarioDto> horarios = obtenerHorariosUseCase.obtenerHorariosPorSeccion(seccionID);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/docente/{docenteID}")
    public ResponseEntity<List<HorarioDto>> obtenerHorariosPorDocente(@PathVariable UUID docenteID) {
        List<HorarioDto> horarios = obtenerHorariosUseCase.obtenerHorariosPorDocente(docenteID);
        return ResponseEntity.ok(horarios);
    }

    @GetMapping("/periodo/{periodoID}")
    public ResponseEntity<List<HorarioDto>> obtenerHorariosPorPeriodo(@PathVariable UUID periodoID) {
        List<HorarioDto> horarios = obtenerHorariosUseCase.obtenerHorariosPorPeriodo(periodoID);
        return ResponseEntity.ok(horarios);
    }

}
