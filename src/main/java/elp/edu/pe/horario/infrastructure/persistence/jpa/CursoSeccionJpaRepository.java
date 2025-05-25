package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoSeccionJpaRepository extends JpaRepository<CursoSeccionEntity, UUID> {

    boolean existsByCursoIdAndSeccionId(UUID cursoId, UUID seccionId);
    boolean existsByCursoId(UUID cursoId);
    boolean existsBySeccionId(UUID seccionId);
}
