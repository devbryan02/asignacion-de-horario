package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.HorarioDto;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import org.springframework.stereotype.Component;

@Component
public class HorarioDtoMapper {

    public HorarioDto toDto(AsignacionHorario domain) {
        return new HorarioDto(
                domain.getId(),
                domain.getCursoSeccionDocente().getCurso().getNombre(),
                domain.getCursoSeccionDocente().getDocente().getNombre(),
                domain.getAula().getNombre(),
                domain.getCursoSeccionDocente().getSeccion().getNombre(),
                domain.getBloqueHorario().getDiaSemana(),
                domain.getBloqueHorario().getHoraInicio(),
                domain.getBloqueHorario().getHoraFin()
        );
    }
}
