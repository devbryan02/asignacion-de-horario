package elp.edu.pe.horario.application.usecase.seccion;

import elp.edu.pe.horario.application.dto.SeccionDto;
import elp.edu.pe.horario.application.mapper.SeccionDtoMapper;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerSeccionesUseCase {

    private final SeccionRepository seccionRepository;
    private final SeccionDtoMapper seccionDtoMapper;

    public ObtenerSeccionesUseCase(SeccionRepository seccionRepository, SeccionDtoMapper seccionDtoMapper) {
        this.seccionRepository = seccionRepository;
        this.seccionDtoMapper = seccionDtoMapper;
    }

    public List<SeccionDto> obtenerSecciones() {
        return seccionRepository.findAll()
                .stream()
                .map(seccionDtoMapper::toDto)
                .toList();
    }

    public Optional<SeccionDto> obtenerSeccionPorId(UUID id) {
        return seccionRepository.findById(id)
                .map(seccionDtoMapper::toDto);
    }
}
