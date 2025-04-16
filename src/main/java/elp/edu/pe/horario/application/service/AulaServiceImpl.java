package elp.edu.pe.horario.application.service;

import elp.edu.pe.horario.application.dto.request.AulaRequest;
import elp.edu.pe.horario.application.dto.response.GenericResponse;
import elp.edu.pe.horario.application.mapper.AulaDtoMapper;
import elp.edu.pe.horario.application.usecase.aula.CrearAulaUseCase;
import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.infrastructure.persistence.repository.AulaRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class AulaServiceImpl implements CrearAulaUseCase {

    private static final Logger log = LoggerFactory.getLogger(AulaServiceImpl.class);
    private final AulaRepositoryImpl aulaRepository;
    private final AulaDtoMapper mapper;

    public AulaServiceImpl(AulaRepositoryImpl aulaRepository, AulaDtoMapper mapper) {
        this.aulaRepository = aulaRepository;
        this.mapper = mapper;
    }

    @Override
    public GenericResponse crear(AulaRequest request) {
        try{
            Aula aula = mapper.toDomain(request);
            aulaRepository.save(aula);
            return GenericResponse.success("Aula creada correctamente");
        }catch (Exception e){
            log.error("Error al crear el aula", e);
            return GenericResponse.failure("Error al crear el aula");
        }
    }
}
