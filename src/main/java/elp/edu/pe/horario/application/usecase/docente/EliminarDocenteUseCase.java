package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.infrastructure.persistence.repository.DocenteRepositoryImpl;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.CustomException;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarDocenteUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarDocenteUseCase.class);
    private final DocenteRepositoryImpl docenteRepository;

    public EliminarDocenteUseCase(DocenteRepositoryImpl docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public void ejecutar(UUID id) {
        try{
            if(id == null) throw new BadRequest("ID no puede ser nulo");

            Docente docente = docenteRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Docente no encontrado"));

            docenteRepository.deleteById(id);

            log.info("Docente eliminado: {}", docente);
        }catch (Exception e){
            log.error("Error al eliminar el docente", e);
            throw new DeleteException("Error al eliminar el docente");
        }
    }
}
