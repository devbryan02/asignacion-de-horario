package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.UnidadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UnidadRepository extends JpaRepository<UnidadEntity, UUID> {
}
