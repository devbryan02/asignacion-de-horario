package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.infrastructure.persistence.entity.PeriodoEntity;
import org.springframework.stereotype.Component;

@Component
public class PeriodoMapper {

    public PeriodoAcademico toDomain(PeriodoEntity entity){
        return new PeriodoAcademico(
                entity.getId(),
                entity.getNombre(),
                entity.getFechaInicio(),
                entity.getFechaFin()
        );
    }

    public PeriodoEntity toEntity(PeriodoAcademico domain){
        var entity = new PeriodoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setFechaInicio(domain.getFechaInicio());
        entity.setFechaFin(domain.getFechaFin());
        return entity;
    }

}
