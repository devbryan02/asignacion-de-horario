package elp.edu.pe.horario.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.util.UUID;

public record BloqueRequest(
    UUID id,
    String diaSemana,
    @JsonFormat(pattern = "HH:mm")
    LocalTime horaInicio,
    @JsonFormat(pattern = "HH:mm")
    LocalTime horaFin,
    String turno
) { }
