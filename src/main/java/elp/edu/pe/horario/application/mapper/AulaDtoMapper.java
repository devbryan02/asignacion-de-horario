package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.AulaDto;
import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.domain.enums.TipoAula;
import elp.edu.pe.horario.domain.model.Aula;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public void updateAula(Aula existente, AulaRequest nuevo){
        existente.setNombre(nuevo.nombre());
        existente.setCapacidad(nuevo.capacidad());
        existente.setTipo(TipoAula.valueOf(nuevo.tipo()));
    }
}
