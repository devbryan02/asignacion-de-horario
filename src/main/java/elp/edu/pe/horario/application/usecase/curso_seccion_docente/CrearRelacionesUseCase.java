package elp.edu.pe.horario.application.usecase.curso_seccion_docente;

import elp.edu.pe.horario.application.dto.request.CursoSeccionDocenteBulkRequest;
import elp.edu.pe.horario.application.mapper.CursoSeccionDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.Seccion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrearRelacionesUseCase {

    private final CursoSeccionDtoMapper mapper;

    public List<CursoSeccionDocente> execute(
            CursoSeccionDocenteBulkRequest request,
            Curso curso,
            List<Seccion> secciones,
            Docente docente){

        List<CursoSeccionDocente> relaciones = new ArrayList<>();

        for (Seccion seccion : secciones) {
            CursoSeccionDocente relacion = mapper.toDomainFromBulk(
                    request, curso, seccion, docente);
            relaciones.add(relacion);
        }

        return relaciones;

    }

}
