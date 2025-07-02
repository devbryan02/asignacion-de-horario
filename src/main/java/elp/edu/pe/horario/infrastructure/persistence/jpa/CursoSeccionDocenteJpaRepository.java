package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CursoSeccionDocenteJpaRepository extends JpaRepository<CursoSeccionDocenteEntity, UUID> {

    boolean existsByCursoIdAndSeccionIdAndDocenteId(UUID cursoId, UUID seccionId, UUID docenteId);
    boolean existsByCursoId(UUID cursoId);
    boolean existsBySeccionId(UUID seccionId);
    boolean existsByCursoIdAndSeccionId(UUID cursoId, UUID seccionId);
    long countByCursoIdAndDocenteId(UUID cursoId, UUID docenteId);

    @Query(value = "EXEC sp_curso_seccion_docente_por_periodo :periodoId", nativeQuery = true)
    List<CursoSeccionDocenteEntity> findByPeriodoId(@Param("periodoId") UUID periodoId);
}

