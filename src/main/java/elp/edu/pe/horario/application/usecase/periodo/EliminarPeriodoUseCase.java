package elp.edu.pe.horario.application.usecase.periodo;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
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
public class EliminarPeriodoUseCase {

    private final static Logger log = LoggerFactory.getLogger(EliminarPeriodoUseCase.class);
    private final PeriodoRepository periodoRepository;
    private final SeccionRepository seccionRepository;

    @Transactional
    public void ejecutar(UUID id){
        try{
            if(id == null) throw new BadRequest("ID no puede ser nulo");

            PeriodoAcademico periodo = periodoRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Periodo no encontrado"));

            // Verificar si el periodo tiene secciones asociadas
            if(seccionRepository.existsByPeriodoId(id)){
                throw new DeleteException("No se puede eliminar el periodo porque tiene secciones asociadas");
            }
            // Eliminar el periodo
            periodoRepository.deleteById(id);
            log.info("Periodo eliminado: {}", periodo);
        } catch (BadRequest | NotFoundException | DeleteException e) {
            // Manejar excepciones específicas
            log.error("Error controlado: {}", e.getMessage());
            throw e; // Relanzar la excepción específica
        }catch (Exception e){
            // Manejar cualquier otro error inesperado
            log.error("Error inesperado al eliminar el periodo", e);
            throw new DeleteException("Error inesperado al eliminar el periodo");
        }
    }
}
