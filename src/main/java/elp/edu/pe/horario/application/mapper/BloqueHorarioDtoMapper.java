package elp.edu.pe.horario.application.mapper;

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
}
