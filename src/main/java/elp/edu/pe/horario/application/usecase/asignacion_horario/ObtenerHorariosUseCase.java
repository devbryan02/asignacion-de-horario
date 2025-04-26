package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.application.dto.HorarioDto;
import elp.edu.pe.horario.application.mapper.HorarioDtoMapper;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ObtenerHorariosUseCase {

    private final static Logger logger = LoggerFactory.getLogger(ObtenerHorariosUseCase.class);
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final HorarioDtoMapper horarioDtoMapper;

    public ObtenerHorariosUseCase(AsignacionHorarioRepository asignacionHorarioRepository, HorarioDtoMapper horarioDtoMapper) {
        this.asignacionHorarioRepository = asignacionHorarioRepository;
        this.horarioDtoMapper = horarioDtoMapper;
    }

    public List<HorarioDto> obtenerHorariosPorSeccion(UUID seccionID) {
        if(seccionID == null) {
            throw new BadRequest("El ID de la sección no puede ser nulo");
        }
        return asignacionHorarioRepository.findBySeccionId(seccionID)
                .stream()
                .map(horarioDtoMapper::toDto)
                .toList();
    }

    public List<HorarioDto> obtenerHorariosPorDocente(UUID docenteID) {
        if(docenteID == null) {
            throw new BadRequest("El ID del docente no puede ser nulo");
        }
        return asignacionHorarioRepository.findByDocenteId(docenteID)
                .stream()
                .map(horarioDtoMapper::toDto)
                .toList();
    }

    public List<HorarioDto> obtenerHorariosPorPeriodo(UUID periodoID) {
        if(periodoID == null) {
            throw new BadRequest("El ID del periodo no puede ser nulo");
        }
        return asignacionHorarioRepository.findByPeriodoId(periodoID)
                .stream()
                .map(horarioDtoMapper::toDto)
                .toList();
    }

}
