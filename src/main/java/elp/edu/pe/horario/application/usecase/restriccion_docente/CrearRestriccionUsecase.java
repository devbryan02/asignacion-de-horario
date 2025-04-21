package elp.edu.pe.horario.application.usecase.restriccion_docente;

import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.RestriccionDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearRestriccionUsecase {

    private final static Logger log = LoggerFactory.getLogger(CrearRestriccionUsecase.class);
    private final RestriccionDocenteRepository restriccionRepository;
    private final RestriccionDtoMapper mapper;
    private final DocenteRepository docenteRepository;

    public CrearRestriccionUsecase(RestriccionDocenteRepository restriccionRepository, RestriccionDtoMapper mapper, DocenteRepository docenteRepository) {
        this.restriccionRepository = restriccionRepository;
        this.mapper = mapper;
        this.docenteRepository = docenteRepository;
    }

    public RegistroResponse ejecutar(RestriccionRequest request){
        try{

            Docente docente = docenteRepository.findById(request.docenteId())
                    .orElseThrow(() -> new NotFoundException("Docente no encontrado"));

            RestriccionDocente restriccionDocente = mapper.toDomain(request, docente);
            restriccionRepository.save(restriccionDocente);
            log.info("Restricción creada: {}", restriccionDocente);
            return RegistroResponse.success("Restricción creada correctamente");
        }catch (Exception e){
            log.error("Error al crear la restricción: {}", e.getMessage());
            return RegistroResponse.failure("Error al crear la restricción");
        }
    }
}
