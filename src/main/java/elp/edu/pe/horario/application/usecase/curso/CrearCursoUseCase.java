package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.CursoDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class CrearCursoUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearCursoUseCase.class);
    private final CursoRepository cursoRepository;
    private final CursoDtoMapper cursoDtoMapper;
    private final UnidadRepository unidadRepository;

    public CrearCursoUseCase(CursoRepository cursoRepository, CursoDtoMapper cursoDtoMapper, UnidadRepository unidadRepository) {
        this.cursoRepository = cursoRepository;
        this.cursoDtoMapper = cursoDtoMapper;
        this.unidadRepository = unidadRepository;
    }

    public RegistroResponse ejecutar(CursoRequest request) {
        try{

            UnidadAcademica unidadAcademica = unidadRepository
                    .findById(request.unidadId())
                    .orElseThrow(() -> new NotFoundException("Unidad académica no encontrada"));

            Curso curso = cursoDtoMapper.toDomain(request, unidadAcademica);
            cursoRepository.save(curso);
            log.info("Curso creado correctamente: {}", curso.getId());
            return RegistroResponse.success("Curso creado correctamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear el curso");
        }
    }
}
