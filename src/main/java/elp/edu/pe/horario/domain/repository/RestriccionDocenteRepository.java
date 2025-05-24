package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.RestriccionDocente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestriccionDocenteRepository {

    List<RestriccionDocente> findAll();
    RestriccionDocente save(RestriccionDocente restriccionDocente);
    Optional<RestriccionDocente> findById(UUID id);
    void deleteById(UUID id);
    List<RestriccionDocente> findAllByDocenteId(UUID docenteId);

    boolean existsByDocenteId(UUID docenteId);

}
