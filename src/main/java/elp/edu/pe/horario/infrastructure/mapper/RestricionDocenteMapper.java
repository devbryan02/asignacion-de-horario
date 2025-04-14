package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.infrastructure.persistence.entity.RestriccionDocenteEntity;
import org.springframework.stereotype.Component;

@Component
public class RestricionDocenteMapper {

    private final DocenteMapper docenteMapper;

    public RestricionDocenteMapper(DocenteMapper docenteMapper) {
        this.docenteMapper = docenteMapper;
    }

    public RestriccionDocente toDomain(RestriccionDocenteEntity entity){
        return new RestriccionDocente(
                entity.getId(),
                entity.getDiaSemana(),
                entity.getHoraInicio(),
                entity.getHoraFin(),
                entity.getTipoRestriccion(),
                docenteMapper.toDomain(entity.getDocente())
        );
    }

    public RestriccionDocenteEntity toEntity(RestriccionDocente domain){
        var entity = new RestriccionDocenteEntity();
        entity.setId(domain.getId());
        entity.setDiaSemana(domain.getDiaSemana());
        entity.setHoraInicio(domain.getHoraInicio());
        entity.setHoraFin(domain.getHoraFin());
        entity.setTipoRestriccion(domain.getTipoRestriccion());
        entity.setDocente(docenteMapper.toEntity(domain.getDocente()));
        return entity;
    }

}
