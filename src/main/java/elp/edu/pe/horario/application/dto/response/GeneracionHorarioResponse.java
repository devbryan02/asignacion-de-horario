package elp.edu.pe.horario.application.dto.response;

public record GeneracionHorarioResponse(
        String mensaje,
        int cantidadAsignaciones,
        int cantidadAulasUsadas,
        int cantidadBloquesUsados,
        int cantidadDocentesAsignados,
        String calidadGeneracion,
        String mensajeEvaluacion
) {}
