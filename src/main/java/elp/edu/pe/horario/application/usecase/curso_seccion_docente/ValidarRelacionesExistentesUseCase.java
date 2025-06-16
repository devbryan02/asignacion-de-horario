package elp.edu.pe.horario.application.usecase.curso_seccion_docente;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.shared.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ValidarRelacionesExistentesUseCase {

    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;

    public void execute(Curso curso, List<Seccion> secciones, Docente docente) {
        for (Seccion seccion : secciones) {

            if (cursoSeccionDocenteRepository.existsByCursoIdAndSeccionIdAndDocenteId(
                    curso.getId(), seccion.getId(), docente.getId())) {
                throw new CustomException(String.format(
                        "Ya has asignado el curso **%s** a la sección **%s** con el docente **%s**.",
                        curso.getNombre(), seccion.getNombre(), docente.getNombre()));
            }

            if (cursoSeccionDocenteRepository.existsByCursoIdAndSeccionId(
                    curso.getId(), seccion.getId())) {
                throw new CustomException(String.format(
                        "El curso **%s** ya fue asignado a la sección **%s** con otro docente.",
                        curso.getNombre(), seccion.getNombre()));
            }
        }
    }

}
