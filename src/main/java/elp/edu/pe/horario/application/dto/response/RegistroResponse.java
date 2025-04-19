package elp.edu.pe.horario.application.dto.response;

public record RegistroResponse(
        boolean success,
        String message
) {
    public static RegistroResponse success(String message) {
        return new RegistroResponse(true, message);
    }

    public static RegistroResponse failure(String message) {
        return new RegistroResponse(false, message);
    }
}
