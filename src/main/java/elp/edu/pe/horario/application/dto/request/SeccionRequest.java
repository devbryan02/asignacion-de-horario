package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record SeccionRequest(
        String nombre,
        UUID periodoAcademicoId
) { }
