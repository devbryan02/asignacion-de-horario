package elp.edu.pe.horario.application.usecase.curso;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.DeleteException;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EliminarCursoUseCase {

    private static final Logger log = LoggerFactory.getLogger(EliminarCursoUseCase.class);
    private final CursoRepository cursoRepository;
    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;

    @Transactional
    public void ejecutar(UUID id) {
        try {
            if (id == null) throw new BadRequest("ID no puede ser nulo");

            Curso curso = cursoRepository
                    .findById(id)
                    .orElseThrow(() -> new NotFoundException("Curso no encontrado"));

            // Verificar referencias en curso_seccion
            boolean tieneReferencias = cursoSeccionDocenteRepository.existsByCursoId(id);
            log.info("¿Tiene referencias en curso_seccion?: {}", tieneReferencias);
            if (tieneReferencias) {
                throw new DeleteException("No se puede eliminar el curso porque está siendo utilizado en secciones.");
            }

            cursoRepository.deleteById(id);
            log.info("Curso eliminado: {}", curso);

        } catch (BadRequest | NotFoundException | DeleteException e) {
            // Manejar excepciones específicas
            log.error("Error controlado: {}", e.getMessage());
            throw e; // Relanzar la excepción específica
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            log.error("Error inesperado al eliminar el curso", e);
            throw new DeleteException("Error inesperado al eliminar el curso");
        }
    }
}
