package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.infrastructure.persistence.entity.AulaEntity;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper {

    public Aula toDomain(AulaEntity entity){
        if (entity == null) {
            return null;
        }

        return new Aula(
                entity.getId(),
                entity.getNombre(),
                entity.getCapacidad(),
                entity.getTipo()
        );
    }

    public AulaEntity toEntity(Aula domain){
        if (domain == null) {
            return null;
        }
        var entity = new AulaEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setCapacidad(domain.getCapacidad());
        entity.setTipo(domain.getTipo());
        return entity;
    }
}