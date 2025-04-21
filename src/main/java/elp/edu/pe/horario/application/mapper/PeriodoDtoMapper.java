package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.request.PeriodoRequest;
import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import org.springframework.stereotype.Component;

@Component
public class PeriodoDtoMapper {

    public PeriodoAcademico toDomain(PeriodoRequest request){
        return new PeriodoAcademico(
                null,
                request.nombre(),
                request.fechaInicio(),
                request.fechaFin()
        );
    }
}
