package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    private final UnidadMapper unidadMapper;

    public CursoMapper(UnidadMapper unidadMapper) {
        this.unidadMapper = unidadMapper;
    }

    public Curso toDomain(CursoEntity entity){
        return new Curso(
                entity.getId(),
                entity.getNombre(),
                entity.getHorasSemanales(),
                entity.getTipo(),
                unidadMapper.toDomain(entity.getUnidad())
        );
    }

    public CursoEntity toEntity(Curso domain){
        var entity = new CursoEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setHorasSemanales(domain.getHorasSemanales());
        entity.setTipo(domain.getTipo());
        entity.setUnidad(unidadMapper.toEntity(domain.getUnidad()));
        return entity;
    }

}
