package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CursoJpaRepository extends JpaRepository<CursoEntity, UUID> {

    boolean existsByNombre(String nombre);
    boolean existsByCodigo(String codigo);

}
