package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.application.dto.UserDto;
import elp.edu.pe.horario.application.mapper.UserDtoMapper;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import elp.edu.pe.horario.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindUsersUseCase {

    private final UserJpaRepository userJpaRepository;
    private final UserDtoMapper mapper;

    public List<UserDto> findAll() {

        return userJpaRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserDto findById(Long id) {
        return userJpaRepository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomException("Usuario no econtrado con id: " + id));
    }

    public UserDto findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(mapper::toDto)
                .orElseThrow(() -> new CustomException("Usuario no econtrado con username: " + username));
    }

}
