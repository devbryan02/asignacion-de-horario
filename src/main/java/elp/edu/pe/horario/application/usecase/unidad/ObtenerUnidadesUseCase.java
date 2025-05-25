package elp.edu.pe.horario.application.usecase.unidad;

import elp.edu.pe.horario.application.dto.UnidadDto;
import elp.edu.pe.horario.application.mapper.UnidadDtoMapper;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerUnidadesUseCase {

    private final UnidadRepository unidadRepository;
    private final UnidadDtoMapper unidadDtoMapper;

    public ObtenerUnidadesUseCase(UnidadRepository unidadRepository, UnidadDtoMapper unidadDtoMapper) {
        this.unidadRepository = unidadRepository;
        this.unidadDtoMapper = unidadDtoMapper;
    }

    @Transactional
    public List<UnidadDto> obtenerUnidades(){
        return unidadRepository.findAll()
                .stream()
                .map(unidadDtoMapper::toDto)
                .toList();
    }

    public Optional<UnidadDto> obtenerUnidadPorId(UUID id){
        return unidadRepository.findById(id)
                .map(unidadDtoMapper::toDto);
    }
}
