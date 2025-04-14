package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.infrastructure.persistence.entity.UnidadEntity;
import org.springframework.stereotype.Component;

@Component
public class UnidadMapper {

    public UnidadAcademica toDomain(UnidadEntity entity){
        return new UnidadAcademica(
                entity.getId(),
                entity.getNombre()
        );
    }

    public UnidadEntity toEntity(UnidadAcademica unidadAcademica){
        var entity = new UnidadEntity();
        entity.setId(unidadAcademica.getId());
        entity.setNombre(unidadAcademica.getNombre());
        return entity;
    }

}
