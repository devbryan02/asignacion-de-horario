package elp.edu.pe.horario.application.dto.request;

public record UpdateUserRequest(
        String username,
        String password,
        String role,
        boolean enabled
) { }
