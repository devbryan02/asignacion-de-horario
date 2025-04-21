package elp.edu.pe.horario.application.usecase.bloque_horario;

import elp.edu.pe.horario.application.dto.request.BloqueRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.BloqueHorarioDtoMapper;
import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearBloqueUseCase {

    private final static Logger log = LoggerFactory.getLogger(CrearBloqueUseCase.class);
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final BloqueHorarioDtoMapper mapper;

    public CrearBloqueUseCase(BloqueHorarioRepository bloqueHorarioRepository, BloqueHorarioDtoMapper mapper) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(BloqueRequest request){
        try{
            BloqueHorario bloqueHorario = mapper.toDomain(request);
            bloqueHorarioRepository.save(bloqueHorario);
            log.info("Bloque horario creado: {}", bloqueHorario);
            return RegistroResponse.success("Bloque horario creado correctamente");
        }catch (Exception e){
            log.error("Error al crear el bloque horario: {}", e.getMessage());
            return RegistroResponse.failure("Error al crear el bloque horario");
        }
    }


}
