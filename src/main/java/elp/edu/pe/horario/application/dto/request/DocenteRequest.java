package elp.edu.pe.horario.application.dto.request;

import java.util.UUID;

public record DocenteRequest(
    UUID id,
    String nombre,
    Integer horasContradadas
) { }
