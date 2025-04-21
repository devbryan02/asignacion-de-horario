package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.model.Seccion;
import org.springframework.stereotype.Component;

@Component
public class SeccionDtoMapper {

    public Seccion toDomain(SeccionRequest request, PeriodoAcademico periodo){
        return new Seccion(
                null,
                request.nombre(),
                periodo
        );
    }
}
