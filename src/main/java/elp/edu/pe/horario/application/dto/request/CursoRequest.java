package elp.edu.pe.horario.application.dto.request;

import java.util.List;
import java.util.UUID;

public record CursoRequest(
        String nombre,
        Integer horasSemanales,
        String tipo,
        List<UUID> unidadesIds
) { }
