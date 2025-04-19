package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AsignacionHorarioJpaRepository extends JpaRepository<AsignacionHorarioEntity, UUID> {

}
