package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.request.CursoSeccionRequest;
import elp.edu.pe.horario.domain.enums.ModoClase;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccion;
import elp.edu.pe.horario.domain.model.Seccion;
import org.springframework.stereotype.Component;

@Component
public class CursoSeccionDtoMapper {

    public CursoSeccion toDomain(CursoSeccionRequest request,Curso curso, Seccion seccion ) {
        return new CursoSeccion(
             null,
                curso,
                seccion,
                ModoClase.valueOf(request.modo())

        );
    }
}
