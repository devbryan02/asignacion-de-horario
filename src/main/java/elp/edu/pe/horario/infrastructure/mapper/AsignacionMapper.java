package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AsignacionMapper {

    private final AulaMapper aulaMapper;
    private final BloqueHorarioMapper bloqueHorarioMapper;
    private final CursoSeccionDocenteMapper cursoSeccionDocenteMapper;


    public AsignacionHorario toDomain(AsignacionHorarioEntity entity){
        if( entity == null) {
            return null;
        }
        return new AsignacionHorario(
                entity.getId(),
                cursoSeccionDocenteMapper.toDomain(entity.getCursoSeccion()),
                aulaMapper.toDomain(entity.getAula()),
                bloqueHorarioMapper.toDomain(entity.getBloqueHorario())
        );
    }

    public AsignacionHorarioEntity toEntity(AsignacionHorario domain){
        if (domain == null) {
            return null;
        }
        var entity = new AsignacionHorarioEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setCursoSeccion(cursoSeccionDocenteMapper.toEntity(domain.getCursoSeccionDocente()));
        entity.setAula(aulaMapper.toEntity(domain.getAula()));
        entity.setBloqueHorario(bloqueHorarioMapper.toEntity(domain.getBloqueHorario()));
        return entity;
    }
}
