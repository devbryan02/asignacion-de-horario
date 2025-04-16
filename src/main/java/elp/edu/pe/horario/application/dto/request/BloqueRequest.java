package elp.edu.pe.horario.application.dto.request;

import java.time.LocalTime;
import java.util.UUID;

public record BloqueRequest(
    UUID id,
    Integer diaSemana,
    LocalTime horaInicio,
    LocalTime horaFin,
    String turno
) { }
