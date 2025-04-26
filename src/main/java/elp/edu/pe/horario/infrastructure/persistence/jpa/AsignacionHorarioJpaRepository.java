package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.UUID;

public interface AsignacionHorarioJpaRepository extends JpaRepository<AsignacionHorarioEntity, UUID> {

    //obtenerasignacionpordocenteId
    List<AsignacionHorarioEntity> findByDocenteId(UUID docenteId);

}
