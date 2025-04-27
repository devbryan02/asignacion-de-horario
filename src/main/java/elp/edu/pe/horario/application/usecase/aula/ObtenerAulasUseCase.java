package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.AulaDto;
import elp.edu.pe.horario.application.mapper.AulaDtoMapper;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerAulasUseCase {

    private final AulaRepository aulaRepository;
    private final AulaDtoMapper mapper;

    public ObtenerAulasUseCase(AulaRepository aulaRepository, AulaDtoMapper mapper) {
        this.aulaRepository = aulaRepository;
        this.mapper = mapper;
    }

    public List<AulaDto> obtenerAulas() {
        return aulaRepository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<AulaDto> obtenerAulaPorId(UUID id) {
        return aulaRepository.findById(id)
                .map(mapper::toDto);
    }
}
