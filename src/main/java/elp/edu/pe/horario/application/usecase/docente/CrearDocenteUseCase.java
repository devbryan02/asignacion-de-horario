package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.infrastructure.persistence.repository.DocenteRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearDocenteUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearDocenteUseCase.class);
    private final DocenteRepositoryImpl docenteRepository;
    private final DocenteDtoMapper mapper;

    public CrearDocenteUseCase(DocenteRepositoryImpl docenteRepository, DocenteDtoMapper mapper) {
        this.docenteRepository = docenteRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(DocenteRequest request) {
        try{
            Docente docente = mapper.toDomain(request);
            docenteRepository.save(docente);
            return RegistroResponse.success("Docente creado correctamente");
        }catch (Exception e){
            log.error("Error al crear el docente", e);
            return RegistroResponse.failure("Error al crear el docente");
        }
    }
}
