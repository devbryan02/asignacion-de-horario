package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.response.AuthResponse;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import elp.edu.pe.horario.shared.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserLoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserJpaRepository userJpaRepository;

    public AuthResponse execute(AuthRequest request) {
        try {
            // First check if user exists
            var user = userJpaRepository.findByUsername(request.username())
                    .orElseThrow(() -> new UsernameNotFoundException("El usuario no existe"));

            try {
                // Then attempt authentication
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );
            } catch (BadCredentialsException e) {
                return new AuthResponse(
                        false,
                        request.username(),
                        "Credenciales inválidas",
                        null,
                        null
                );
            }

            // If authentication successful, generate token
            String token = jwtUtils.generateToken(user.getUsername(), user.getRole());
            return new AuthResponse(
                    true,
                    user.getUsername(),
                    "Inicio de sesión exitoso",
                    user.getRole(),
                    token
            );

        } catch (UsernameNotFoundException e) {
            return new AuthResponse(
                    false,
                    request.username(),
                    "Usuario no encontrado",
                    null,
                    null
            );
        } catch (Exception e) {
            log.error("Error al iniciar sesión: {}", e.getMessage());
            return new AuthResponse(
                    false,
                    request.username(),
                    "Error inesperado al iniciar sesión",
                    null,
                    null
            );
        }
    }
}