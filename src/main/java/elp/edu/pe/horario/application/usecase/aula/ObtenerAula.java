package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.AulaDto;

import java.util.Optional;
import java.util.UUID;

public interface ObtenerAula {

    Optional<AulaDto> obtener(UUID id);

}
