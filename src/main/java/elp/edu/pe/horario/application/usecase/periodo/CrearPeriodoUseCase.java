package elp.edu.pe.horario.application.usecase.periodo;

import elp.edu.pe.horario.application.dto.request.PeriodoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.PeriodoDtoMapper;
import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearPeriodoUseCase {

    private final static Logger log = LoggerFactory.getLogger(CrearPeriodoUseCase.class);
    private final PeriodoRepository periodoRepository;
    private final PeriodoDtoMapper mapper;

    public CrearPeriodoUseCase(PeriodoRepository periodoRepository, PeriodoDtoMapper mapper) {
        this.periodoRepository = periodoRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(PeriodoRequest request){
        try{
            PeriodoAcademico periodo = mapper.toDomain(request);
            periodoRepository.save(periodo);
            return RegistroResponse.success("Periodo creado correctamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear el periodo");
        }
    }
}
