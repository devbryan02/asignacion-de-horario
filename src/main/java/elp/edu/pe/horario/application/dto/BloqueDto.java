package elp.edu.pe.horario.application.dto;

import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.Turno;

import java.time.LocalTime;
import java.util.UUID;

public record BloqueDto(
        UUID id,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        Turno turno
)
{ }
