package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface DocenteJpaRepository extends JpaRepository<DocenteEntity, UUID> {

}
