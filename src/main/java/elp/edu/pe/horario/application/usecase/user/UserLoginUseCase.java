package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.response.AuthResponse;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import elp.edu.pe.horario.shared.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserJpaRepository userJpaRepository;

    public AuthResponse execute (AuthRequest request){
         try{
             authenticationManager.authenticate(
                     new UsernamePasswordAuthenticationToken(
                             request.username(),
                             request.password()
                     )
             );
             var user = userJpaRepository.findByUsername(request.username())
                     .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
             String token = jwtUtils.generateToken(user.getUsername(), user.getRole());

             return new AuthResponse(
                     true,
                     user.getUsername(),
                     "Inicio de sesión exitoso",
                     user.getRole(),
                     token
             );
         }catch (Exception e){
                return new AuthResponse(
                        false,
                        request.username(),
                        "Error al iniciar sesión: " + e.getMessage(),
                        null,
                        null
                );
         }

    }
}
