package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.application.dto.CursoDto;
import elp.edu.pe.horario.application.mapper.CursoDtoMapper;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ObtenerCursosUseCase {

    private final CursoRepository cursoRepository;
    private final CursoDtoMapper cursoDtoMapper;

    public ObtenerCursosUseCase(CursoRepository cursoRepository, CursoDtoMapper cursoDtoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoDtoMapper = cursoDtoMapper;
    }

    @Transactional
    public List<CursoDto> obtenerCursos(){
        return cursoRepository.findAll()
                .stream()
                .map(cursoDtoMapper::toDto)
                .toList();
    }

    public Optional<CursoDto> obtenerCursoPorId(UUID id){
        return cursoRepository.findById(id)
                .map(cursoDtoMapper::toDto);
    }

}
