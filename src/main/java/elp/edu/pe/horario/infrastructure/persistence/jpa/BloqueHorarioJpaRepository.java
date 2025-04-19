package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.BloqueHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloqueHorarioJpaRepository extends JpaRepository<BloqueHorarioEntity, UUID> {

}
