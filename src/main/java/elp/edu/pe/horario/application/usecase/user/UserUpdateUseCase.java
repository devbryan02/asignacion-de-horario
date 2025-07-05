package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.application.dto.request.UpdateUserRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.UserDtoMapper;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUpdateUseCase {

    private final UserJpaRepository userJpaRepository;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegistroResponse execute(UpdateUserRequest request) {

        try{
            var existingUser = userJpaRepository.findById(request.idUser())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            mapper.updateToEntity(existingUser, request, passwordEncoder );
            userJpaRepository.save(existingUser);
            return RegistroResponse.success("Roles y permisos actualizados correctamente para el usuario: " + existingUser.getUsername());
        }catch (Exception e){
            return RegistroResponse.failure("Error al actualizar el usuario: " + e.getMessage());
        }

    }
}
