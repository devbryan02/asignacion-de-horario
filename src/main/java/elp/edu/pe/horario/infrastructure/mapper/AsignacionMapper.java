package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import org.springframework.stereotype.Component;

@Component
public class AsignacionMapper {

    private final DocenteMapper docenteMapper;
    private final AulaMapper aulaMapper;
    private final BloqueHorarioMapper bloqueHorarioMapper;
    private final CursoSeccionMapper cursoSeccionMapper;

    public AsignacionMapper(DocenteMapper docenteMapper, AulaMapper aulaMapper, BloqueHorarioMapper bloqueHorarioMapper, CursoSeccionMapper cursoSeccionMapper) {
        this.docenteMapper = docenteMapper;

        this.aulaMapper = aulaMapper;
        this.bloqueHorarioMapper = bloqueHorarioMapper;
        this.cursoSeccionMapper = cursoSeccionMapper;
    }

    public AsignacionHorario toDomain(AsignacionHorarioEntity entity){
        return new AsignacionHorario(
                entity.getId(),
                docenteMapper.toDomain(entity.getDocente()),
                cursoSeccionMapper.toDomain(entity.getCursoSeccion()),
                aulaMapper.toDomain(entity.getAula()),
                bloqueHorarioMapper.toDomain(entity.getBloqueHorario())
        );
    }

    public AsignacionHorarioEntity toEntity(AsignacionHorario domain){
        var entity = new AsignacionHorarioEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setDocente(docenteMapper.toEntity(domain.getDocente()));
        entity.setCursoSeccion(cursoSeccionMapper.toEntity(domain.getCursoSeccion()));
        entity.setAula(aulaMapper.toEntity(domain.getAula()));
        entity.setBloqueHorario(bloqueHorarioMapper.toEntity(domain.getBloqueHorario()));
        return entity;
    }
}
