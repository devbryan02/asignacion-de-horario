package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.GenericResponse;

public interface ActualizarAula {

    GenericResponse actualizar(AulaRequest request);

}
