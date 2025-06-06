package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.RestriccionDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RestriccionDocenteJpaRepository extends JpaRepository<RestriccionDocenteEntity, UUID> {

    // Consultas personalizadas pueden ser añadidas aquí
    List<RestriccionDocenteEntity> findAllByDocenteId(UUID docenteId);

    boolean existsByDocenteId(UUID docenteId);


}
