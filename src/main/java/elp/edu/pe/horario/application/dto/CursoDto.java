package elp.edu.pe.horario.application.dto;

import java.util.UUID;

public record CursoDto(
        UUID id,
        String nombre,
        Integer horasSemanales,
        String tipo,
        Integer unidadesAcademicasCount
)
{ }
