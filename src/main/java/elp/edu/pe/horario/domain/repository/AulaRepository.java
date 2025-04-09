package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AulaRepository extends JpaRepository<AulaEntity, UUID> {

}
