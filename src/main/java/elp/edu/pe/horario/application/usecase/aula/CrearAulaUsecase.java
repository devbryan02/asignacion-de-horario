package elp.edu.pe.horario.application.usecase.aula;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.AulaDtoMapper;
import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearAulaUsecase {

    private static final Logger log = LoggerFactory.getLogger(CrearAulaUsecase.class);
    private final AulaRepository aulaRepository;
    private final AulaDtoMapper mapper;

    public CrearAulaUsecase(AulaRepository aulaRepository, AulaDtoMapper mapper) {
        this.aulaRepository = aulaRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(AulaRequest request) {
        try{
            Aula aula = mapper.toDomain(request);
            aulaRepository.save(aula);
            return RegistroResponse.success("Aula creada correctamente");
        }catch (Exception e){
            log.error("Error al crear el aula", e);
            return RegistroResponse.failure("Error al crear el aula");
        }
    }
}
