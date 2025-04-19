package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.infrastructure.persistence.repository.DocenteRepositoryImpl;
import elp.edu.pe.horario.shared.exception.CustomException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
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
            Optional<Docente> docente = docenteRepository.findById(id);
            if (docente.isEmpty()) throw new NotFoundException("Docente no encontrado");
            if(id == null) throw new CustomException("El id no puede ser nulo");
            docenteRepository.deleteById(id);
        }catch (Exception e){
            log.error("Error al eliminar el docente", e);
        }
    }
}
