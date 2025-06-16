package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.CursoDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CrearCursoUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearCursoUseCase.class);
    private final CursoRepository cursoRepository;
    private final CursoDtoMapper cursoDtoMapper;
    private final UnidadRepository unidadRepository;


    public RegistroResponse ejecutar(CursoRequest request) {
        try{

            Curso curso = cursoDtoMapper.toDomain(request);

            List<UUID> unidadesIds = request.unidadesIds();
            if(unidadesIds != null && !unidadesIds.isEmpty()){
                List<UnidadAcademica> unidades = unidadRepository.findAllById(unidadesIds);
                if (unidades.size() != unidadesIds.size()) {
                    return RegistroResponse.failure("Una o más unidades no existen");
                }
                curso.setUnidades(unidades);
            }
            cursoRepository.save(curso);
            log.info("Curso creado correctamente: {}", curso);
            return RegistroResponse.success("Curso creado correctamente");
        }catch (Exception e){
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear el curso");
        }
    }
}
