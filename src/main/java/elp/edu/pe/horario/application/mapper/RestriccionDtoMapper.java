package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.RestriccionDocenteDto;
import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.TipoRestriccion;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import org.springframework.stereotype.Component;

@Component
public class RestriccionDtoMapper {

    public RestriccionDocente toDomain(RestriccionRequest request, Docente docente) {
        return new RestriccionDocente(
                null,
                DiaSemana.valueOf(request.diaSemana().toUpperCase()),
                request.horaInicio(),
                request.horaFin(),
                TipoRestriccion.valueOf(request.tipoRestriccion().toUpperCase()),
                docente
        );
    }

    public RestriccionDocenteDto toDto(RestriccionDocente restriccionDocente){
        return new RestriccionDocenteDto(
                restriccionDocente.getId(),
                restriccionDocente.getDocente().getNombre(),
                restriccionDocente.getDiaSemana(),
                restriccionDocente.getHoraInicio(),
                restriccionDocente.getHoraFin(),
                restriccionDocente.getTipoRestriccion()
        );
    }
}
