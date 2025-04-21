package elp.edu.pe.horario.application.usecase.unidad;

import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EliminarUnidadUseCase {

    private final static Logger log = LoggerFactory.getLogger(EliminarUnidadUseCase.class);
    private final UnidadRepository unidadRepository;

    public EliminarUnidadUseCase(UnidadRepository unidadRepository) {
        this.unidadRepository = unidadRepository;
    }

    public void ejecutar(UUID id){
        try{
            if(id == null) throw new BadRequest("ID no puede ser nulo");

            UnidadAcademica unidadAcademica = unidadRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Unidad Academica no encontrada"));

            unidadRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
}
