package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.BloqueDto;
import elp.edu.pe.horario.application.dto.request.BloqueRequest;
import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.Turno;
import elp.edu.pe.horario.domain.model.BloqueHorario;
import org.springframework.stereotype.Component;

@Component
public class BloqueHorarioDtoMapper {

    public BloqueHorario toDomain(BloqueRequest request) {
        return new BloqueHorario(
                null,
                DiaSemana.valueOf(request.diaSemana()),
                request.horaInicio(),
                request.horaFin(),
                Turno.valueOf(request.turno())

        );
    }

    public BloqueDto toDto(BloqueHorario bloqueHorario) {
        return new BloqueDto(
                bloqueHorario.getId(),
                bloqueHorario.getDiaSemana(),
                bloqueHorario.getHoraInicio(),
                bloqueHorario.getHoraFin(),
                bloqueHorario.getTurno()
        );
    }

    public void updateBloque(BloqueHorario existente, BloqueRequest nuevo) {
        existente.setDiaSemana(DiaSemana.valueOf(nuevo.diaSemana()));
        existente.setHoraInicio(nuevo.horaInicio());
        existente.setHoraFin(nuevo.horaFin());
        existente.setTurno(Turno.valueOf(nuevo.turno()));
    }
}
