package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
            if(id == null) throw new BadRequest("ID no puede ser nulo");

            Curso curso = cursoRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

            cursoRepository.deleteById(id);

            log.info("Curso eliminado: {}", curso);
        }catch (Exception e){
            log.error("Error al eliminar el curso", e);
            throw new DeleteException("Error al eliminar el docente");
        }

    }
}
