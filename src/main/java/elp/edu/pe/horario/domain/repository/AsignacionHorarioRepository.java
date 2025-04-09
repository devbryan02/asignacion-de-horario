package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.AsignacionHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AsignacionHorarioRepository extends JpaRepository<AsignacionHorarioEntity, UUID> {
}
