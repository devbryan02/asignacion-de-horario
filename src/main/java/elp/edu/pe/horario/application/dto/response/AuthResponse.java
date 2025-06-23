package elp.edu.pe.horario.application.dto.response;

public record AuthResponse (
        boolean success,
        String username,
        String message,
        String role,
        String token
)
{}

