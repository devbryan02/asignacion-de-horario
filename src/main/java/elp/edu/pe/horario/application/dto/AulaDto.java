package elp.edu.pe.horario.application.dto;

import elp.edu.pe.horario.domain.enums.TipoAula;

import java.util.UUID;

public record AulaDto(
        UUID id,
        String nombre,
        Integer capacidad,
        TipoAula tipo
) { }
