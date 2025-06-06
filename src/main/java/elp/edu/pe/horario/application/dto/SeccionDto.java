package elp.edu.pe.horario.application.dto;

import java.util.UUID;

public record SeccionDto(
    UUID id,
    String nombre,
    String periodoAcademico,
    String fechaInicio,
    String fechaFin
) { }
