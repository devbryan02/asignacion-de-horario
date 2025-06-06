package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.UnidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UnidadJpaRepository extends JpaRepository<UnidadEntity, UUID> {

}
