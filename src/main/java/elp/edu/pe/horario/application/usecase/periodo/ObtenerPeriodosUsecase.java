package elp.edu.pe.horario.application.usecase.periodo;

import elp.edu.pe.horario.application.dto.PeriodoDto;
import elp.edu.pe.horario.application.mapper.PeriodoDtoMapper;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerPeriodosUsecase {

    private final PeriodoRepository periodoRepository;
    private final PeriodoDtoMapper mapper;

    public ObtenerPeriodosUsecase(PeriodoRepository periodoRepository, PeriodoDtoMapper mapper) {
        this.periodoRepository = periodoRepository;
        this.mapper = mapper;
    }

    public List<PeriodoDto> obtenerPeriodos() {
        return periodoRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<PeriodoDto> obtenerPeriodoPorId(UUID id) {
        return periodoRepository.findById(id)
                .map(mapper::toDto);
    }
}
