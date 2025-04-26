package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AsignacionHorarioJpaRepository extends JpaRepository<AsignacionHorarioEntity, UUID> {

    List<AsignacionHorarioEntity> findByCursoSeccion_Seccion_Id(UUID seccionId);

    List<AsignacionHorarioEntity> findByDocente_Id(UUID docenteId);

    List<AsignacionHorarioEntity> findByCursoSeccion_Seccion_Periodo_Id(UUID periodoId);

}
