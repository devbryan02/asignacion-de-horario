package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocenteRepository extends JpaRepository<DocenteEntity, UUID> {
}
