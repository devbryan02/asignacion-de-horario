package elp.edu.pe.horario.application.dto;

import java.util.List;
import java.util.UUID;

public record DocenteDto(
        UUID id,
        String nombre,
        Integer horasContratadas,
        Integer horasMaximasPorDia,
        List<RestriccionDto> restricciones
) { }
