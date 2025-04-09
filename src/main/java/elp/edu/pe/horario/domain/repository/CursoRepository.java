package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoRepository extends JpaRepository<CursoEntity, UUID> {
}
