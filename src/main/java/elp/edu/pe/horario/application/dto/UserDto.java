package elp.edu.pe.horario.application.dto;

public record UserDto(
        Long id,
        String username,
        String role,
        boolean enabled
) { }
