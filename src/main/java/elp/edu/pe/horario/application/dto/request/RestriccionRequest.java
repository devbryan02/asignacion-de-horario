package elp.edu.pe.horario.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import java.util.UUID;

public record RestriccionRequest(
        String diaSemana,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFin,
        String tipoRestriccion,
        UUID idDocente
) { }
