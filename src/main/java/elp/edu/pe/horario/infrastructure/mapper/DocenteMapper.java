package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import org.springframework.stereotype.Component;

@Component
public class DocenteMapper {

    private final UnidadMapper mapper;

    public DocenteMapper(UnidadMapper mapper) {
        this.mapper = mapper;
    }

    public Docente toDomain(DocenteEntity entity) {
        Docente docente = new  Docente(
                entity.getId(),
                entity.getNombre(),
                entity.getHorasContratadas(),
                entity.getHorasMaximasPorDia()
        );
        if(entity.getUnidades() != null){
            docente.setUnidadesAcademicas(
                    entity.getUnidades()
                            .stream()
                            .map(mapper::toDomain)
                            .toList()
            );
        }
        return docente;
    }

    public DocenteEntity toEntity(Docente domain) {
        var entity = new DocenteEntity();
        entity.setId(domain.getId());
        entity.setNombre(domain.getNombre());
        entity.setHorasContratadas(domain.getHorasContratadas());
        entity.setHorasMaximasPorDia(domain.getHorasMaximasPorDia());

        if(domain.getUnidadesAcademicas() != null){
            entity.setUnidades(
                    domain.getUnidadesAcademicas()
                            .stream()
                            .map(mapper::toEntity)
                            .toList()
            );
        }
        return entity;
    }
}
