package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.SeccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeccionJpaRepository extends JpaRepository<SeccionEntity, UUID> {


}
