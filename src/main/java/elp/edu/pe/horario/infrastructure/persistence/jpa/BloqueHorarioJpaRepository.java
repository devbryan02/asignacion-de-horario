package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.BloqueHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BloqueHorarioJpaRepository extends JpaRepository<BloqueHorarioEntity, UUID> {

    // Verificar referencias en asignacion_horario
    @Query("SELECT COUNT(a) > 0 FROM AsignacionHorarioEntity a WHERE a.bloqueHorario.id = :id")
    boolean existeReferenciaEnAsignacionHorario(UUID id);
}
