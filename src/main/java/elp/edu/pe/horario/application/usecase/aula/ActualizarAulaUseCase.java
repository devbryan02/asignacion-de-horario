package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.AulaDtoMapper;
import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ActualizarAulaUseCase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarAulaUseCase.class);
    private final AulaRepository aulaRepository;
    private final AulaDtoMapper mapper;

    public ActualizarAulaUseCase(AulaRepository aulaRepository, AulaDtoMapper mapper) {
        this.aulaRepository = aulaRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(AulaRequest aulaNuevo, UUID aulaId) {

        try{
            Aula aulaExistente = aulaRepository.findById(aulaId)
                    .orElseThrow(() -> new NotFoundException("Aula no encontrada"));

            mapper.updateAula(aulaExistente, aulaNuevo);
            aulaRepository.update(aulaExistente);
            log.info("Aula actualizada: {}", aulaExistente);

            return RegistroResponse.success("Aula actualizada correctamente");
        }catch (Exception e){
            log.error("Error al actualizar el aula", e);
            return RegistroResponse.failure("Error al actualizar el aula");
        }
    }

}
