package elp.edu.pe.horario.application.usecase.curso_seccion_docente;

import elp.edu.pe.horario.application.dto.request.CursoSeccionDocenteBulkRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.CustomException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearCursoSeccionDocenteBulkUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearCursoSeccionDocenteBulkUseCase.class);
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;
    private final DocenteRepository docenteRepository;
    private final CrearRelacionesUseCase crearRelacionesUseCase;
    private final ValidarRelacionesExistentesUseCase validarRelacionesExistentesUseCase;

    @Transactional
    public RegistroResponse ejecutar(CursoSeccionDocenteBulkRequest request) {
        try {
            Curso curso = obtenerCurso(request.cursoId());
            List<Seccion> secciones = obtenerSecciones(request.seccionesIds());
            Docente docente = obtenerDocente(request.docenteId());

            this.validarRelacionesExistentesUseCase.execute(curso, secciones, docente);

            List<CursoSeccionDocente> relaciones = this.crearRelacionesUseCase.execute(request, curso, secciones, docente);
            cursoSeccionDocenteRepository.saveAll(relaciones);

            log.info("Relaciones curso-sección-docente creadas: {}", relaciones.size());
            return RegistroResponse.success("Se registraron " + relaciones.size() +
                    " relaciones curso-sección-docente correctamente");

        } catch (IllegalArgumentException e) {
            log.warn("Validación fallida: {}", e.getMessage());
            return RegistroResponse.failure(e.getMessage());
        } catch (CustomException e) {
            log.warn("Error al crear relaciones curso-sección-docente: {}", e.getMessage());
            return RegistroResponse.failure(e.getMessage());
        } catch (NotFoundException e) {
            log.warn("Error al encontrar curso, sección o docente: {}", e.getMessage());
            return RegistroResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado: {}", e.getMessage());
            return RegistroResponse.failure("Error al registrar relaciones curso-sección-docente");
        }
    }

    // Métodos privados para obtener entidades
    private Curso obtenerCurso(UUID cursoId) {
        return cursoRepository.findById(cursoId)
                .orElseThrow(() -> new NotFoundException("Curso no encontrado"));
    }

    private List<Seccion> obtenerSecciones(List<UUID> seccionesIds) {
        return seccionesIds.stream()
                .map(id -> seccionRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Sección no encontrada: " + id)))
                .toList();
    }

    private Docente obtenerDocente(UUID docenteId) {
        return docenteRepository.findById(docenteId)
                .orElseThrow(() -> new NotFoundException("Docente no encontrado"));
    }

}