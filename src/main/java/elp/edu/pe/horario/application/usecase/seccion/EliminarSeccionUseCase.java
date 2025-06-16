package elp.edu.pe.horario.application.usecase.seccion;

import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EliminarSeccionUseCase {

    private final static Logger log = LoggerFactory.getLogger(EliminarSeccionUseCase.class);
    private final SeccionRepository seccionRepository;
    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;

    @Transactional
    public void ejecutar(UUID id) {
        try {
            if (id == null) throw new BadRequest("El id no puede ser nulo");

            Seccion seccion = seccionRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Sección no encontrada"));

            // Verificar si la sección está asociada a algún curso
            if (cursoSeccionDocenteRepository.existsBySeccionId(id)) {
                throw new DeleteException("No se puede eliminar la sección porque está asociada a un curso ");
            }

            // Eliminar la sección
            seccionRepository.deleteById(id);
            log.info("Sección eliminada: {}", seccion);
        } catch (BadRequest | NotFoundException | DeleteException e) {
            // Manejar excepciones específicas
            log.error("Error controlado: {}", e.getMessage());
            throw e; // Relanzar la excepción específica
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            log.error("Error inesperado al eliminar la sección", e);
            throw new DeleteException("Error inesperado al eliminar la sección");
        }
    }
}
