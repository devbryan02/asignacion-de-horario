package elp.edu.pe.horario.application.dto.request;

public record AulaRequest(
        String nombre,
        Integer capacidad,
        String tipo
) { }
