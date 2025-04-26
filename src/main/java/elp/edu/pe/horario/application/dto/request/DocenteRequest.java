package elp.edu.pe.horario.application.dto.request;

import java.util.List;
import java.util.UUID;

public record DocenteRequest(
        String nombre,
        Integer horasContratadas,
        Integer horasMaximasPorDia,
        List<UUID> unidadesIds
) { }
