package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.RestriccionDocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestriccionDocenteJpaRepository extends JpaRepository<RestriccionDocenteEntity, UUID> {

    // Consultas personalizadas pueden ser añadidas aquí
}
