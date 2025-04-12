package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.entity.RestriccionDocente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestriccionDocenteRepository extends JpaRepository<RestriccionDocente, UUID> {
}
