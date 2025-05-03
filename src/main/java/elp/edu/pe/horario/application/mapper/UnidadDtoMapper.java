package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.UnidadDto;
import elp.edu.pe.horario.application.dto.request.UnidadRequest;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import org.springframework.stereotype.Component;

@Component
public class UnidadDtoMapper {

    public UnidadAcademica toDomain(UnidadRequest request){
        return new UnidadAcademica(
                null,
                request.nombre()
        );
    }

    public UnidadDto toDto(UnidadAcademica unidadAcademica){
        return new UnidadDto(
                unidadAcademica.getId(),
                unidadAcademica.getNombre()
        );
    }
}
