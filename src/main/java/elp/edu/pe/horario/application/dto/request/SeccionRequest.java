package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record SeccionRequest(
        UUID id,
        String nombre,
        UUID periodoAcademicoId
) { }
