package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record PeriodoRequest(
    UUID id,
    String nombre,
    String fechaInicio,
    String fechaFin
) { }
