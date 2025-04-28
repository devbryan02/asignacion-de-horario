package elp.edu.pe.horario.application.dto;

import java.time.LocalDate;
import java.util.UUID;

public record PeriodoDto(
        UUID id,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin
) { }
