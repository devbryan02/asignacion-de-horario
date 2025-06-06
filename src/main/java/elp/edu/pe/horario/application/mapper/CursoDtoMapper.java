package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.CursoDto;
import elp.edu.pe.horario.application.dto.request.CursoRequest;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import org.springframework.stereotype.Component;

@Component
public class CursoDtoMapper {

    public Curso toDomain(CursoRequest request, UnidadAcademica unidadAcademica){
        return new Curso(
                null,
                request.nombre(),
                request.horasSemanales(),
                request.tipo(),
                unidadAcademica
        );
    }

    public CursoDto toDto(Curso curso) {
        return new CursoDto(
                curso.getId(),
                curso.getNombre(),
                curso.getHorasSemanales(),
                curso.getTipo(),
                curso.getUnidad().getNombre()
        );
    }

    public void updateCurso(Curso existente, CursoRequest nuevo){
        existente.setNombre(nuevo.nombre());
        existente.setHorasSemanales(nuevo.horasSemanales());
        existente.setTipo(nuevo.tipo());
        // No se actualiza la unidad, ya que no se permite cambiarla
    }
}
