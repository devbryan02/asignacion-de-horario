package elp.edu.pe.horario.application.usecase.seccion;

import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.SeccionDtoMapper;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActualizarSeccionUseCase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarSeccionUseCase.class);
    private final SeccionRepository seccionRepository;
    private final SeccionDtoMapper seccionDtoMapper;


    public RegistroResponse ejecutar(SeccionRequest nuevo, UUID seccionId){
        try{

            if(seccionId == null) throw new BadRequest("ID no puede ser nulo");

            Seccion seccionExistente = seccionRepository.findById(seccionId)
                    .orElseThrow(() -> new NotFoundException("Seccion no encontrada"));

            seccionDtoMapper.toUpdate(seccionExistente, nuevo);
            seccionRepository.update(seccionExistente);

            log.info("Seccion actualizada: {}", seccionExistente);

            return RegistroResponse.success("Seccion actualizada correctamente");

        }catch (Exception e){
            log.error("Error al actualizar la seccion: {}", e.getMessage());
            return RegistroResponse.failure("Error al actualizar la seccion");
        }
    }

}
