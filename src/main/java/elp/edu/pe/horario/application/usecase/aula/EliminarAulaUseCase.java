package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarAulaUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarAulaUseCase.class);
    private final AulaRepository aulaRepository;

    public EliminarAulaUseCase(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    public void ejecutar(UUID id) {
        try {
            if (id == null) throw new BadRequest("ID no puede ser nulo");

            Aula aula = aulaRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Aula no encontrada"));

            // Verificar referencias en asignacion_horario
            boolean tieneReferencias = aulaRepository.existeReferenciaEnAsignacionHorario(id);
            if (tieneReferencias) {
                throw new DeleteException("No se puede eliminar el aula porque está siendo utilizada en asignaciones de horario.");
            }

            aulaRepository.deleteById(id);
            log.info("Aula eliminada: {}", aula);
        } catch (DeleteException e) {
            log.error("Error al eliminar el aula: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al eliminar el aula", e);
            throw new DeleteException("Error al eliminar el aula");
        }
    }
}
