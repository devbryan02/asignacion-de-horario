package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import org.springframework.stereotype.Component;

@Component
public class DocenteMapper {

    public Docente toDomain(DocenteEntity entity) {
        return new Docente(
                entity.getId(),
                entity.getNombre(),
                entity.getHorasContradadas()
        );
    }

    public DocenteEntity toEntity(Docente domain) {
        var entity = new DocenteEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setHorasContradadas(domain.getHorasContradadas());
        return entity;
    }
}
