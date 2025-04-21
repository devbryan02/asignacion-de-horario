package elp.edu.pe.horario.application.usecase.unidad;

import elp.edu.pe.horario.application.dto.request.UnidadRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.UnidadDtoMapper;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearUnidadUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearUnidadUseCase.class);
    private final UnidadRepository unidadRepository;
    private final UnidadDtoMapper unidadDtoMapper;

    public CrearUnidadUseCase(UnidadRepository unidadRepository, UnidadDtoMapper unidadDtoMapper) {
        this.unidadRepository = unidadRepository;
        this.unidadDtoMapper = unidadDtoMapper;
    }

    public RegistroResponse ejecutar(UnidadRequest request){
        try{
            UnidadAcademica unidadAcademica = unidadDtoMapper.toDomain(request);
            unidadRepository.save(unidadAcademica);
            return RegistroResponse.success("Unidad Academica creada correctamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear la unidad academica");
        }
    }

}
