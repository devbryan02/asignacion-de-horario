package elp.edu.pe.horario.application.dto;

import elp.edu.pe.horario.domain.enums.DiaSemana;

import java.time.LocalTime;
import java.util.UUID;

public record HorarioDto(
        UUID id,
        String curso,
        String docente,
        String aula,
        String seccion,
        DiaSemana diaSemana,
        LocalTime horaInicio,
        LocalTime horaFin
) {}

