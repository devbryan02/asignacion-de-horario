package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record AulaRequest(
        UUID id,
        String nombre,
        Integer capacidad,
        String tipo
) { }
