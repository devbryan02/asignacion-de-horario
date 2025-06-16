package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper {

    private final UnidadMapper unidadMapper;

    public CursoMapper(UnidadMapper unidadMapper) {this.unidadMapper = unidadMapper;}

    public Curso toDomain(CursoEntity entity){

        if(entity == null) return null;

        Curso curso = new Curso();

        curso.setId(entity.getId());
        curso.setNombre(entity.getNombre());
        curso.setHorasSemanales(entity.getHorasSemanales());
        curso.setTipo(entity.getTipo());

        if(entity.getUnidades() != null){
            curso.setUnidades(
                entity.getUnidades()
                    .stream()
                    .map(unidadMapper::toDomain)
                    .toList()
            );
        }

        return  curso;
    }

    public CursoEntity toEntity(Curso domain){

        if(domain == null) return null;

        var entity = new CursoEntity();

        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setHorasSemanales(domain.getHorasSemanales());
        entity.setTipo(domain.getTipo());

        if(domain.getUnidades()!=null){
            entity.setUnidades(
                domain.getUnidades()
                    .stream()
                    .map(unidadMapper::toEntity)
                    .toList()
            );
        }

        return entity;
    }

}
