package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.PeriodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PeriodoJpaRepository extends JpaRepository<PeriodoEntity, UUID> {



}
