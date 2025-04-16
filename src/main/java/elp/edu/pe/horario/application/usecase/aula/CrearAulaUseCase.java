package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.GenericResponse;
import org.springframework.stereotype.Service;

@Service
public interface CrearAulaUseCase {

    GenericResponse crear(AulaRequest request);
}
