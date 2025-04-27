package elp.edu.pe.horario.application.usecase.bloque_horario;


import elp.edu.pe.horario.application.dto.BloqueDto;
import elp.edu.pe.horario.application.mapper.BloqueHorarioDtoMapper;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerBloquesUseCase {

    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final BloqueHorarioDtoMapper mapper;

    public ObtenerBloquesUseCase(BloqueHorarioRepository bloqueHorarioRepository, BloqueHorarioDtoMapper mapper) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.mapper = mapper;
    }

    public List<BloqueDto> obtenerBloques(){
        return bloqueHorarioRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public Optional<BloqueDto> obtenerBloquePorId(UUID id){
        return bloqueHorarioRepository.findById(id)
                .map(mapper::toDto);
    }

}
