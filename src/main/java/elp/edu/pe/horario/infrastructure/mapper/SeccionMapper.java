package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.infrastructure.persistence.entity.SeccionEntity;
import org.springframework.stereotype.Component;

@Component
public class SeccionMapper {

    private final PeriodoMapper periodoMapper;

    public SeccionMapper(PeriodoMapper periodoMapper) {
        this.periodoMapper = periodoMapper;
    }

    public Seccion toDomain(SeccionEntity entity){
        return new Seccion(
                entity.getId(),
                entity.getNombre(),
                periodoMapper.toDomain(entity.getPeriodo())
        );
    }

    public SeccionEntity toEntity(Seccion domain){
        var entity = new SeccionEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setPeriodo(periodoMapper.toEntity(domain.getPeriodo()));
        return entity;
    }

}
