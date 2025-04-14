package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AulaJpaRepository extends JpaRepository<AulaEntity, UUID> {

    boolean existsById(UUID id);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, UUID id);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, UUID id);
}
