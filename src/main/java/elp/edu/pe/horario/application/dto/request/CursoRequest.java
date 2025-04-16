package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record CursoRequest(
        String nombre,
        Integer horasSemanales,
        String tipo,
        UUID unidadId
) { }
