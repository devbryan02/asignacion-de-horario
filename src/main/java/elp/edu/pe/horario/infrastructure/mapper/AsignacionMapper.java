package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import org.springframework.stereotype.Component;

@Component
public class AsignacionMapper {

    private final DocenteMapper docenteMapper;
    private final CursoMapper cursoMapper;
    private final AulaMapper aulaMapper;
    private final BloqueHorarioMapper bloqueHorarioMapper;

    public AsignacionMapper(DocenteMapper docenteMapper, CursoMapper cursoMapper, AulaMapper aulaMapper, BloqueHorarioMapper bloqueHorarioMapper) {
        this.docenteMapper = docenteMapper;
        this.cursoMapper = cursoMapper;
        this.aulaMapper = aulaMapper;
        this.bloqueHorarioMapper = bloqueHorarioMapper;
    }

    public AsignacionHorario toDomain(AsignacionHorarioEntity entity){
        return new AsignacionHorario(
                entity.getId(),
                docenteMapper.toDomain(entity.getDocente()),
                cursoMapper.toDomain(entity.getCurso()),
                aulaMapper.toDomain(entity.getAula()),
                bloqueHorarioMapper.toDomain(entity.getBloqueHorario())
        );
    }

    public AsignacionHorarioEntity toEntity(AsignacionHorario domain){
        var entity = new AsignacionHorarioEntity();
        entity.setId(domain.getId());
        entity.setDocente(docenteMapper.toEntity(domain.getDocente()));
        entity.setCurso(cursoMapper.toEntity(domain.getCurso()));
        entity.setAula(aulaMapper.toEntity(domain.getAula()));
        entity.setBloqueHorario(bloqueHorarioMapper.toEntity(domain.getBloqueHorario()));
        return entity;
    }
}
