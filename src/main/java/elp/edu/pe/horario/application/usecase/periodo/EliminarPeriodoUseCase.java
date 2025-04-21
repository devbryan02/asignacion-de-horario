package elp.edu.pe.horario.application.usecase.periodo;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarPeriodoUseCase {

    private final static Logger log = LoggerFactory.getLogger(EliminarPeriodoUseCase.class);
    private final PeriodoRepository periodoRepository;

    public EliminarPeriodoUseCase(PeriodoRepository periodoRepository) {
        this.periodoRepository = periodoRepository;
    }

    public void ejecutar(UUID id){
        try{
            if(id == null) throw new BadRequest("ID no puede ser nulo");

            PeriodoAcademico periodo = periodoRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Periodo no encontrado"));

            periodoRepository.deleteById(id);

            log.info("Periodo eliminado: {}", periodo);
        }catch (Exception e){
            log.error("Error al eliminar el periodo", e);
            throw new DeleteException("Error al eliminar el periodo");
        }
    }
}
