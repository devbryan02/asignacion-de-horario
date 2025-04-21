package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EliminarCursoUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarCursoUseCase.class);
    private final CursoRepository cursoRepository;

    public EliminarCursoUseCase(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public void ejecutar(UUID id){
        try{
            Optional<Curso> curso = cursoRepository.findById(id);
            if (curso.isEmpty()) throw new NotFoundException("Curso no encontrado");
            if(id == null) throw new BadRequest("El id no puede ser nulo");
            cursoRepository.deleteById(id);
        }catch (Exception e){
            log.error("Error al eliminar el curso", e);
        }

    }
}
