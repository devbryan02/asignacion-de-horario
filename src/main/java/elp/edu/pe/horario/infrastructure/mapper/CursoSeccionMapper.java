package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionDocenteEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CursoSeccionMapper {

    private final CursoMapper cursoMapper;
    private final SeccionMapper seccionMapper;
    private final DocenteMapper docenteMapper;

    public CursoSeccionDocente toDomain(CursoSeccionDocenteEntity entity){
        return new CursoSeccionDocente(
                entity.getId(),
                cursoMapper.toDomain(entity.getCurso()),
                seccionMapper.toDomain(entity.getSeccion()),
                docenteMapper.toDomain(entity.getDocente()),
                entity.getModo()
        );
    }

    public CursoSeccionDocenteEntity toEntity(CursoSeccionDocente domain){
        var entity = new CursoSeccionDocenteEntity();
        entity.setId(domain.getId());
        entity.setCurso(cursoMapper.toEntity(domain.getCurso()));
        entity.setSeccion(seccionMapper.toEntity(domain.getSeccion()));
        entity.setDocente(docenteMapper.toEntity(domain.getDocente()));
        entity.setModo(domain.getModo());
        return entity;
    }

}
