package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Curso;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoRepository {

    List<Curso> findAll();
    Optional<Curso> findById(UUID id);
    Curso save(Curso curso);
    void deleteById(UUID id);
}
