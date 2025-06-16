package elp.edu.pe.horario.application.dto.request;

import java.util.List;
import java.util.UUID;

public record CursoSeccionDocenteBulkRequest(
        UUID cursoId,
        List<UUID> seccionesIds,
        UUID docenteId,
        String modo
) {}
