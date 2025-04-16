package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record AsignacionHorario(
        UUID id,
        UUID docenteId,
        UUID cursoId,
        UUID aulaId,
        UUID bloqueHorarioId
) { }
