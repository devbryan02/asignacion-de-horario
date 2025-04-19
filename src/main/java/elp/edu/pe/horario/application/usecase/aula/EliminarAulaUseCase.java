package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.infrastructure.persistence.repository.AulaRepositoryImpl;
import elp.edu.pe.horario.shared.exception.CustomException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EliminarAulaUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarAulaUseCase.class);
    private final AulaRepositoryImpl aulaRepository;

    public EliminarAulaUseCase(AulaRepositoryImpl aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    public void ejecutar(UUID id) {
        try{
            Optional<Aula> aula = aulaRepository.findById(id);

            if (aula.isEmpty()) throw new NotFoundException("Aula no encontrada");
            if(id == null) throw new CustomException("El id no puede ser nulo");

            aulaRepository.deleteById(id);
        }catch (Exception e){
            log.error("Error al eliminar el aula", e);
        }
    }

}
