package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.AulaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AulaJpaRepository extends JpaRepository<AulaEntity, UUID> {

    @Query("SELECT COUNT(a) > 0 FROM AsignacionHorarioEntity a WHERE a.aula.id = :aulaId")
    boolean existeReferenciaEnAsignacionHorario(@Param("aulaId") UUID aulaId);

    // Query para obtener aulas por periodo
    @Query(value = "EXEC sp_aulas_disponibles_entidad :periodoId", nativeQuery = true)
    List<AulaEntity> findByPeriodoId(@Param("periodoId") UUID periodoId);

}

