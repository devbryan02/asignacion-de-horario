package elp.edu.pe.horario.application.usecase.curso_seccion;

import elp.edu.pe.horario.application.dto.request.CursoSeccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.CursoSeccionDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccion;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.CursoSeccionRepository;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CrearCursoSeccionUsecase {

    private final static Logger log = LoggerFactory.getLogger(CrearCursoSeccionUsecase.class);
    private final CursoSeccionRepository cursoSeccionRepository;
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final CursoSeccionDtoMapper mapper;

    public CrearCursoSeccionUsecase(CursoSeccionRepository cursoSeccionRepository, CursoRepository cursoRepository, SeccionRepository seccionRepository, CursoSeccionDtoMapper mapper) {
        this.cursoSeccionRepository = cursoSeccionRepository;
        this.cursoRepository = cursoRepository;
        this.seccionRepository = seccionRepository;
        this.mapper = mapper;
    }

    public RegistroResponse ejecutar(CursoSeccionRequest request){
        try{
            //buscamos curso
            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

            //buscamos seccion
            Seccion seccion = seccionRepository.findById(request.seccionId())
                    .orElseThrow(() -> new NotFoundException("Seccion no encontrado"));

            CursoSeccion cursoSeccion = mapper.toDomain(request,curso, seccion);
            cursoSeccionRepository.save(cursoSeccion);
            log.info("CursoSeccion creado: {}", cursoSeccion);
            return RegistroResponse.success("CursoSeccion creado correctamente");
        }catch (Exception e){
            log.error("Error al crear el cursoSeccion: {}", e.getMessage());
            return RegistroResponse.failure("Error al crear el cursoSeccion");
        }
    }

}
