package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record CursoSeccionRequest(
        UUID cursoId,
        UUID seccionId,
        String modo
) { }
