package elp.edu.pe.horario.application.usecase.curso_seccion;

import elp.edu.pe.horario.application.dto.request.CursoSeccionBulkRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.CursoSeccionDtoMapper;
import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.model.CursoSeccion;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.CursoSeccionRepository;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrearCursoSeccionBulkUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearCursoSeccionBulkUseCase.class);
    private final CursoRepository cursoRepository;
    private final SeccionRepository seccionRepository;
    private final CursoSeccionRepository cursoSeccionRepository;
    private final CursoSeccionDtoMapper mapper;

    public CrearCursoSeccionBulkUseCase(CursoRepository cursoRepository, SeccionRepository seccionRepository,
                                        CursoSeccionRepository cursoSeccionRepository, CursoSeccionDtoMapper mapper) {
        this.cursoRepository = cursoRepository;
        this.seccionRepository = seccionRepository;
        this.cursoSeccionRepository = cursoSeccionRepository;
        this.mapper = mapper;
    }

    @Transactional
    public RegistroResponse ejecutar(CursoSeccionBulkRequest request) {
        try {
            // Obtener el curso
            Curso curso = cursoRepository.findById(request.cursoId())
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

            // Comprobar existencia de relaciones antes de intentar crearlas
            List<CursoSeccion> relaciones = request.seccionesIds().stream()
                    .map(seccionId -> {
                        Seccion seccion = seccionRepository.findById(seccionId)
                                .orElseThrow(() -> new NotFoundException("Sección no encontrada: " + seccionId));
                        // Validar si la relación ya existe
                        if (cursoSeccionRepository.existsByCursoAndSeccion(curso.getId(), seccion.getId())) {
                            throw new IllegalArgumentException(
                                    String.format("La sección '%s' ya está asignada al curso '%s'. Verifique los datos e intente nuevamente.",
                                            seccion.getNombre(), curso.getNombre()));
                        }
                        // Mapeo de la nueva relación
                        return mapper.toDomainFromBulk(request, curso, seccion);
                    }).toList();

            // Guardar las nuevas relaciones
            cursoSeccionRepository.saveAll(relaciones);
            log.info("Relaciones curso-sección creadas: {}", relaciones.size());
            return RegistroResponse.success("Se registraron " + relaciones.size() + " relaciones curso-sección correctamente");

        } catch (IllegalArgumentException e) {
            log.warn("Validación fallida: {}", e.getMessage());
            return RegistroResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error("Error al crear relaciones curso-sección: {}", e.getMessage());
            return RegistroResponse.failure("Error al registrar relaciones curso-sección");
        }
    }
}
