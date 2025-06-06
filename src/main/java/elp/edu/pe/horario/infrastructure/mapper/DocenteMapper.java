package elp.edu.pe.horario.infrastructure.mapper;

import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import org.springframework.stereotype.Component;

@Component
public class DocenteMapper {

    private final UnidadMapper mapper;

    public DocenteMapper(UnidadMapper mapper) {
        this.mapper = mapper;
    }

    public Docente toDomain(DocenteEntity entity) {
        Docente docente = new Docente(
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

        if (entity.getRestricciones() != null) {
            docente.setRestricciones(
                    entity.getRestricciones().stream()
                            .map(restriccionEntity -> new RestriccionDocente(
                                    restriccionEntity.getId(),
                                    restriccionEntity.getDiaSemana(),
                                    restriccionEntity.getHoraInicio(),
                                    restriccionEntity.getHoraFin(),
                                    restriccionEntity.getTipoRestriccion(),
                                    null
                            ))
                            .toList()
            );
        }

        return docente;
    }

    public DocenteEntity toEntity(Docente docente) {
        if (docente == null) return null;

        DocenteEntity entity = new DocenteEntity();
        entity.setId(docente.getId());
        entity.setNombre(docente.getNombre());
        entity.setHorasContratadas(docente.getHorasContratadas());
        entity.setHorasMaximasPorDia(docente.getHorasMaximasPorDia());

        if (docente.getUnidadesAcademicas() != null) {
            entity.setUnidades(docente.getUnidadesAcademicas().stream()
                    .map(mapper::toEntity)
                    .toList());
        }

        return entity;
    }
}