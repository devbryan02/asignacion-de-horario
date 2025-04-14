package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DocenteJpaRepository extends JpaRepository<DocenteEntity, UUID> {

    DocenteEntity findByIdDocente(UUID idDocente);
    DocenteEntity findByIdDocenteAndEstado(UUID idDocente, String estado);

}
