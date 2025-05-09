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
        Docente docente = new  Docente(
                entity.getId(),
                entity.getNombre(),
                entity.getHorasContratadas(),
                entity.getHorasMaximasPorDia()
        );
        //seteamos las unidades academicas al docente
        if(entity.getUnidades() != null){
            docente.setUnidadesAcademicas(
                    entity.getUnidades()
                            .stream()
                            .map(mapper::toDomain)
                            .toList()
            );
        }
        //Seteamos las restricciones al docente
        if (entity.getRestricciones() != null) {
            docente.setRestricciones(
                    entity.getRestricciones().stream()
                            .map(restriccionEntity -> new RestriccionDocente(
                                    restriccionEntity.getId(),
                                    restriccionEntity.getDiaSemana(),
                                    restriccionEntity.getHoraInicio(),
                                    restriccionEntity.getHoraFin(),
                                    restriccionEntity.getTipoRestriccion(),
                                    null // Aquí evitamos la referencia circular al docente
                            ))
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
        return entity;
    }
}
