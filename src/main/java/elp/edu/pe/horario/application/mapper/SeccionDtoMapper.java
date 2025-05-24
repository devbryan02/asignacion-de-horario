package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.SeccionDto;
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
    public SeccionDto toDto(Seccion seccion){
        return new SeccionDto(
                seccion.getId(),
                seccion.getNombre(),
                seccion.getPeriodo().getNombre()
        );
    }

    public void toUpdate(Seccion existente, SeccionRequest nuevo){
        existente.setNombre(nuevo.nombre());
    }
}
