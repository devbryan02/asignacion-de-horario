package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record UnidadRequest(
        UUID id,
        String nombre
) { }
