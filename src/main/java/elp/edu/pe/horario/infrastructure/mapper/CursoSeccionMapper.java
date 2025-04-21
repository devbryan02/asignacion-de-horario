package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.CursoSeccion;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionEntity;
import org.springframework.stereotype.Component;

@Component
public class CursoSeccionMapper {

    private final CursoMapper cursoMapper;
    private final SeccionMapper seccionMapper;

    public CursoSeccionMapper(CursoMapper cursoMapper, SeccionMapper seccionMapper) {
        this.cursoMapper = cursoMapper;
        this.seccionMapper = seccionMapper;
    }

    public CursoSeccion toDomain(CursoSeccionEntity entity){
        return new CursoSeccion(
                entity.getId(),
                cursoMapper.toDomain(entity.getCurso()),
                seccionMapper.toDomain(entity.getSeccion()),
                entity.getModo()
        );
    }

    public CursoSeccionEntity toEntity(CursoSeccion domain){
        var entity = new CursoSeccionEntity();
        entity.setId(domain.getId());
        entity.setCurso(cursoMapper.toEntity(domain.getCurso()));
        entity.setSeccion(seccionMapper.toEntity(domain.getSeccion()));
        entity.setModo(domain.getModo());
        return entity;
    }

}
