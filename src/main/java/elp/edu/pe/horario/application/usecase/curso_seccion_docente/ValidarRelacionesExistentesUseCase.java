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
        // Validar nulos
        validarParametros(curso, secciones, docente);

        // Validar secciones duplicadas
        validarSeccionesDuplicadas(secciones);

        for (Seccion seccion : secciones) {
            // Validar que no exista la misma asignación
            validarAsignacionExistente(curso, seccion, docente);

            // Validar que la sección no tenga ya otro docente para este curso
            validarSeccionDisponible(curso, seccion);

            // Validar que el docente no tenga demasiadas secciones del mismo curso
            validarCargaDocentePorCurso(curso, docente);
        }
    }

    private void validarParametros(Curso curso, List<Seccion> secciones, Docente docente) {
        if (curso == null || curso.getId() == null) {
            throw new CustomException("El curso es inválido");
        }
        if (secciones == null || secciones.isEmpty()) {
            throw new CustomException("Debe especificar al menos una sección");
        }
        if (docente == null || docente.getId() == null) {
            throw new CustomException("El docente es inválido");
        }
    }

    private void validarSeccionesDuplicadas(List<Seccion> secciones) {
        long seccionesUnicas = secciones.stream()
                .map(Seccion::getId)
                .distinct()
                .count();

        if (seccionesUnicas != secciones.size()) {
            throw new CustomException("Hay secciones duplicadas en la solicitud");
        }
    }

    private void validarAsignacionExistente(Curso curso, Seccion seccion, Docente docente) {
        if (cursoSeccionDocenteRepository.existsByCursoIdAndSeccionIdAndDocenteId(
                curso.getId(), seccion.getId(), docente.getId())) {
            throw new CustomException(String.format(
                    "Ya has asignado el curso **%s** a la sección **%s** con el docente **%s**.",
                    curso.getNombre(), seccion.getNombre(), docente.getNombre()));
        }
    }

    private void validarSeccionDisponible(Curso curso, Seccion seccion) {
        if (cursoSeccionDocenteRepository.existsByCursoIdAndSeccionId(
                curso.getId(), seccion.getId())) {
            throw new CustomException(String.format(
                    "El curso **%s** ya fue asignado a la sección **%s** con otro docente.",
                    curso.getNombre(), seccion.getNombre()));
        }
    }

    private void validarCargaDocentePorCurso(Curso curso, Docente docente) {
        long seccionesAsignadas = cursoSeccionDocenteRepository
                .countByCursoIdAndDocenteId(curso.getId(), docente.getId());

        if (seccionesAsignadas >= 3) { // Máximo 3 secciones del mismo curso por docente
            throw new CustomException(String.format(
                    "El docente **%s** ya tiene el máximo de secciones permitidas para el curso **%s**",
                    docente.getNombre(), curso.getNombre()));
        }
    }
}