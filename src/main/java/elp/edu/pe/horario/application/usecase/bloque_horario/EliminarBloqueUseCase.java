package elp.edu.pe.horario.application.usecase.bloque_horario;

import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarBloqueUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarBloqueUseCase.class);
    private final BloqueHorarioRepository bloqueHorarioRepository;

    public EliminarBloqueUseCase(BloqueHorarioRepository bloqueHorarioRepository) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
    }

    @Transactional
    public void ejecutar(UUID id){
        try{

            if (id == null) throw new BadRequestException("ID no puede ser nulo");

            BloqueHorario bloqueHorario = bloqueHorarioRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Bloque horario no encontrado"));

            // Verificar referencias en asignacion_horario
            boolean tieneReferencias = bloqueHorarioRepository.existeReferenciaEnAsignacionHorario(id);
            if (tieneReferencias) {
                throw new DeleteException("No se puede eliminar el bloque horario porque tiene referencias en asignacion_horario");
            }
            this.bloqueHorarioRepository.deleteById(id);
            log.info("Bloque horario eliminado: {}", bloqueHorario);

        } catch (Exception e) {
            log.error("Error al eliminar el bloque horario: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }



}
