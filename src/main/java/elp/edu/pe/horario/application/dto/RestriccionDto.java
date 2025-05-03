package elp.edu.pe.horario.application.dto;

import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.TipoRestriccion;

import java.time.LocalTime;
import java.util.UUID;

public record RestriccionDto(
        UUID id,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin,
        TipoRestriccion tipoRestriccion
) { }
