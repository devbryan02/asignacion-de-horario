package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Seccion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeccionRepository {

    List<Seccion> findAll();
    Seccion save(Seccion seccion);
    Optional<Seccion> findById(UUID id);
    void deleteById(UUID id);
    void update(Seccion seccion);
    boolean existsByPeriodoId(UUID periodoId);
}
