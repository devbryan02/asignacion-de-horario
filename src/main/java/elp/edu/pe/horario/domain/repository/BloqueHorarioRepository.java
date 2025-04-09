package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.BloqueHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BloqueHorarioRepository extends JpaRepository<BloqueHorarioEntity, UUID> {
}
