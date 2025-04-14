package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Docente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DocenteRepository {

    List<Docente> findAll();
    Optional<Docente> findById(UUID id);
    Docente save(Docente docente);
    void deleteById(UUID id);
}
