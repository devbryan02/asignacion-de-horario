package elp.edu.pe.horario.application.usecase.seccion;

import elp.edu.pe.horario.application.dto.request.SeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.SeccionDtoMapper;
import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearSeccionUseCase {

    private final static Logger log = LoggerFactory.getLogger(CrearSeccionUseCase.class);
    private final SeccionRepository seccionRepository;
    private final SeccionDtoMapper seccionDtoMapper;
    private final PeriodoRepository periodoRepository;

    public CrearSeccionUseCase(SeccionRepository seccionRepository, SeccionDtoMapper seccionDtoMapper, PeriodoRepository periodoRepository) {
        this.seccionRepository = seccionRepository;
        this.seccionDtoMapper = seccionDtoMapper;
        this.periodoRepository = periodoRepository;
    }

    public RegistroResponse ejecutar(SeccionRequest request){
        try{

            PeriodoAcademico periodoAcademico = periodoRepository
                    .findById(request.periodoAcademicoId())
                    .orElseThrow(() -> new NotFoundException("Periodo académico no encontrado"));

            Seccion seccion = seccionDtoMapper.toDomain(request, periodoAcademico);
            seccionRepository.save(seccion);
            log.info("Sección creada: {}", seccion);
            return RegistroResponse.success("Sección creada correctamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear la sección");
        }
    }

}
