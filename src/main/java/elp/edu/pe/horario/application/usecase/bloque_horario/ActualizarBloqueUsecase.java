package elp.edu.pe.horario.application.usecase.bloque_horario;

import elp.edu.pe.horario.application.dto.request.BloqueRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.BloqueHorarioDtoMapper;
import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActualizarBloqueUsecase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarBloqueUsecase.class);
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final BloqueHorarioDtoMapper mapper;

    public ActualizarBloqueUsecase(BloqueHorarioRepository bloqueHorarioRepository,
                                   BloqueHorarioDtoMapper mapper) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.mapper = mapper;
    }

    @Transactional
    public RegistroResponse ejecutar(BloqueRequest nuevo, UUID bloqueID){

        try{
            BloqueHorario bloqueExistente = bloqueHorarioRepository.findById(bloqueID)
                    .orElseThrow(() -> new NotFoundException("Bloque no encontrado"));

            this.mapper.updateBloque(bloqueExistente, nuevo);
            this.bloqueHorarioRepository.update(bloqueExistente);

            log.info("Bloque actualizado: {}", bloqueExistente);
            return RegistroResponse.success("Bloque actualizado correctamente");

        }catch (Exception e){
            log.error("Error al actualizar el bloque: {}", e.getMessage());
            return RegistroResponse.failure("Error al actualizar el bloque");
        }

    }

}
