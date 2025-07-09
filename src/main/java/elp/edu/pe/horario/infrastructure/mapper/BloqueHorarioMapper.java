package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.infrastructure.persistence.entity.BloqueHorarioEntity;
import org.springframework.stereotype.Component;

@Component
public class BloqueHorarioMapper {

    public BloqueHorario toDomain(BloqueHorarioEntity entity){
        if (entity == null) {
            return null;
        }
        return new BloqueHorario(
                entity.getId(),
                entity.getDiaSemana(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getTurno()
        );
    }

    public BloqueHorarioEntity toEntity(BloqueHorario domain){
        if (domain == null) {
            return null;
        }
        var entity = new BloqueHorarioEntity();
        entity.setId(domain.getId());
        entity.setDiaSemana(domain.getDiaSemana());
        entity.setHoraInicio(domain.getHoraInicio());
        entity.setHoraFin(domain.getHoraFin());
        entity.setTurno(domain.getTurno());
        return entity;
    }

}
