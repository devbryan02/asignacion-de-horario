package elp.edu.pe.horario.application.dto.response;

public record GenericResponse(
        boolean success,
        String message
) {
    public static GenericResponse success(String message) {
        return new GenericResponse(true, message);
    }

    public static GenericResponse failure(String message) {
        return new GenericResponse(false, message);
    }
}
