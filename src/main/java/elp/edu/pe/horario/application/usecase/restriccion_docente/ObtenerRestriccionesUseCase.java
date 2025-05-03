package elp.edu.pe.horario.application.usecase.restriccion_docente;

import elp.edu.pe.horario.application.dto.RestriccionDocenteDto;
import elp.edu.pe.horario.application.mapper.RestriccionDtoMapper;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerRestriccionesUseCase {

    private final RestriccionDocenteRepository restriccionDocenteRepository;
    private final RestriccionDtoMapper mapper;

    public ObtenerRestriccionesUseCase(RestriccionDocenteRepository restriccionDocenteRepository, RestriccionDtoMapper mapper) {
        this.restriccionDocenteRepository = restriccionDocenteRepository;
        this.mapper = mapper;
    }

    public List<RestriccionDocenteDto> obtenerRestricciones(){
        return restriccionDocenteRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<RestriccionDocenteDto> obtenerRestriccionPorId(UUID id){
        return restriccionDocenteRepository.findById(id)
                .map(mapper::toDto);
    }
}
