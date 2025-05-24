package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.CursoDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActualizarCursoUseCase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarCursoUseCase.class);
    private final CursoRepository cursoRepository;
    private final CursoDtoMapper cursoDtoMapper;

    public ActualizarCursoUseCase(CursoRepository cursoRepository, CursoDtoMapper cursoDtoMapper) {
        this.cursoRepository = cursoRepository;
        this.cursoDtoMapper = cursoDtoMapper;
    }

    public RegistroResponse ejecutar(CursoRequest cursoNuevo, UUID cursoId){
        try {
            Curso cursoExistente = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

            cursoDtoMapper.updateCurso(cursoExistente, cursoNuevo);
            cursoRepository.update(cursoExistente);
            log.info("Curso actualizado: {}", cursoExistente);
            return RegistroResponse.success("Curso actualizado correctamente");
        } catch (Exception e) {
            log.error("Error al actualizar el curso", e);
            return RegistroResponse.failure("Error al actualizar el curso");
        }
    }

}
