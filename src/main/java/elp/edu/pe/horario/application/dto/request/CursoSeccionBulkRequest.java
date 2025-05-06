package elp.edu.pe.horario.application.dto.request;

import java.util.List;
import java.util.UUID;

public record CursoSeccionBulkRequest(
        UUID cursoId,
        List<UUID> seccionesIds,
        String modo
) {}
