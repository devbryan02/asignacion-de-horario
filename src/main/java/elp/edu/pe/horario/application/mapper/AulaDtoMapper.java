package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.AulaDto;
import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.domain.enums.TipoAula;
import elp.edu.pe.horario.domain.model.Aula;
import org.springframework.stereotype.Component;

@Component
public class AulaDtoMapper {

    public Aula toDomain(AulaRequest request){
        return new Aula(
                null,
                request.nombre(),
                request.capacidad(),
                TipoAula.valueOf(request.tipo())
        );
    }

    public AulaDto toDto(Aula aula){
        return new AulaDto(
                aula.getId(),
                aula.getNombre(),
                aula.getCapacidad(),
                aula.getTipo()
        );
    }
}
