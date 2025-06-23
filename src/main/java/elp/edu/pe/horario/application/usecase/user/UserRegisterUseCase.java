package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.response.RegisterResponse;
import elp.edu.pe.horario.application.mapper.UserDtoMapper;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import elp.edu.pe.horario.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegisterUseCase {

    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    public RegisterResponse execute(AuthRequest request){
        try{
            // Check if user already exists
            existByUsername(request.username());

            final String USER_ROLE = "USER"; // rol por defecto para usuarios nuevos

            // Map request to entity and encode password
            var userEntity = userDtoMapper.toEntity(request, passwordEncoder, USER_ROLE);
            userJpaRepository.save(userEntity);

            return new RegisterResponse(
                    true,
                    "Registro exitoso"
            );

        } catch (Exception e) {
            return new RegisterResponse(
                    false,
                    "Error al registrar el usuario: " + e.getMessage()
            );
        }
    }

    void existByUsername(String username) {
        if (userJpaRepository.existsByUsername(username)) {
            throw new CustomException("El usuario: " + username + " ya existe");
        }
    }
}
