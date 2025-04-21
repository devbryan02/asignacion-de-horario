package elp.edu.pe.horario.application.usecase.seccion;

import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarSeccionUseCase {

    private final static Logger log = LoggerFactory.getLogger(EliminarSeccionUseCase.class);
    private final SeccionRepository seccionRepository;

    public EliminarSeccionUseCase(SeccionRepository seccionRepository) {
        this.seccionRepository = seccionRepository;
    }

    public void ejecutar(UUID id) {
        try{
            if(id == null) throw new BadRequest("El id no puede ser nulo");

            Seccion seccion = seccionRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Sección no encontrada"));

            seccionRepository.deleteById(id);

            log.info("Sección eliminada: {}", seccion);
        } catch (Exception e) {
            log.error("Error al eliminar la sección: {}", e.getMessage());
            throw new DeleteException("Error al eliminar la sección");
        }
    }
}
