package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.request.CursoSeccionDocenteBulkRequest;
import elp.edu.pe.horario.application.dto.request.CursoSeccionRequest;
import elp.edu.pe.horario.domain.enums.ModoClase;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.Seccion;
import org.springframework.stereotype.Component;

@Component
public class CursoSeccionDtoMapper {

    public CursoSeccionDocente toDomain(CursoSeccionRequest request, Curso curso, Seccion seccion, Docente docente) {
        return new CursoSeccionDocente(
             null,
                curso,
                seccion,
                docente,
                ModoClase.valueOf(request.modo())

        );
    }

    public CursoSeccionDocente toDomainFromBulk(CursoSeccionDocenteBulkRequest request, Curso curso, Seccion seccion, Docente docente) {
        return new CursoSeccionDocente(
                null,
                curso,
                seccion,
                docente,
                ModoClase.valueOf(request.modo())
        );
    }

}
