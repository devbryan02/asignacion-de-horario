package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.dto.DocenteDto;
import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerDocentesUseCase {

    private final DocenteRepository docenteRepository;
    private final DocenteDtoMapper docenteMapper;

    public ObtenerDocentesUseCase(DocenteRepository docenteRepository, DocenteDtoMapper docenteMapper) {
        this.docenteRepository = docenteRepository;
        this.docenteMapper = docenteMapper;
    }

    @Transactional
    public List<DocenteDto> obtenerDocentes() {
        return docenteRepository.findAll()
                .stream()
                .map(docenteMapper::toDtoWithRestricciones)
                .toList();
    }
}
