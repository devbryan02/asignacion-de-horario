package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActualizarDocenteUseCase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarDocenteUseCase.class);
    private final DocenteRepository docenteRepository;
    private final DocenteDtoMapper docenteMapper;

    public ActualizarDocenteUseCase(DocenteRepository docenteRepository, DocenteDtoMapper docenteMapper) {
        this.docenteRepository = docenteRepository;
        this.docenteMapper = docenteMapper;
    }

    @Transactional
    public RegistroResponse ejecutar(DocenteRequest nuevo, UUID id){
        try{
            Docente docenteExistente = docenteRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Docente no encontrado"));
            docenteMapper.updateDocente(docenteExistente, nuevo);
            docenteRepository.update(docenteExistente);
            log.info("Docente actualizado correctamente: {}", docenteExistente);
            return RegistroResponse.success("Docente actualizado correctamente");
        }catch (Exception e){
            log.error("Error al actualizar el docente: {}", e.getMessage());
            return RegistroResponse.failure("Error al actualizar el docente");
        }
    }

}
