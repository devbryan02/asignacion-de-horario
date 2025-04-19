package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.domain.model.Docente;
import org.springframework.stereotype.Component;

@Component
public class DocenteDtoMapper {

    public Docente toDomain(DocenteRequest request){
        return new Docente(
                null,
                request.nombre(),
                request.horasContratadas()
        );
    }

}
