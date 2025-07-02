package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.BloqueHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface BloqueHorarioJpaRepository extends JpaRepository<BloqueHorarioEntity, UUID> {

    // Verificar referencias en asignacion_horario
    @Query("SELECT COUNT(a) > 0 FROM AsignacionHorarioEntity a WHERE a.bloqueHorario.id = :id")
    boolean existeReferenciaEnAsignacionHorario(UUID id);

    //query para obtener bloques por periodo
    @Query(value = "EXEC sp_bloques_disponibles_entidad :periodoId", nativeQuery = true)
    List<BloqueHorarioEntity> findByPeriodoId(@Param("periodoId") UUID periodoId);

}
