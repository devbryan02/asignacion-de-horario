package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.UserDto;
import elp.edu.pe.horario.application.dto.request.AuthRequest;
import elp.edu.pe.horario.application.dto.request.UpdateUserRequest;
import elp.edu.pe.horario.infrastructure.persistence.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public UserEntity toEntity(AuthRequest request, PasswordEncoder passwordEncoder, String role) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.username());
        userEntity.setPassword(passwordEncoder.encode(request.password()));
        userEntity.setRole(role);
        userEntity.setEnabled(true);
        return userEntity;
    }

    public void updateToEntity(UserEntity existingUser, UpdateUserRequest request, PasswordEncoder passwordEncoder) {
        existingUser.setUsername(request.username());
        existingUser.setPassword(passwordEncoder.encode(request.password()));
        existingUser.setRole(request.role());
        existingUser.setEnabled(request.enabled());
    }

    public UserDto toDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getId(),
                userEntity.getUsername(),
                userEntity.getRole(),
                userEntity.isEnabled()
        );
    }

}
