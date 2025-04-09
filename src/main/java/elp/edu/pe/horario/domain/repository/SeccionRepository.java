package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.SeccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeccionRepository extends JpaRepository<SeccionEntity, UUID> {
}
