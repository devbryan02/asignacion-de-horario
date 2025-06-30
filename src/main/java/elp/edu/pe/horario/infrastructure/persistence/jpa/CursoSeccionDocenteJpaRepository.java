package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoSeccionDocenteJpaRepository extends JpaRepository<CursoSeccionDocenteEntity, UUID> {

    boolean existsByCursoIdAndSeccionIdAndDocenteId(UUID cursoId, UUID seccionId, UUID docenteId);
    boolean existsByCursoId(UUID cursoId);
    boolean existsBySeccionId(UUID seccionId);
    boolean existsByCursoIdAndSeccionId(UUID cursoId, UUID seccionId);
    long countByCursoIdAndDocenteId(UUID cursoId, UUID docenteId);
}

