package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoRepository {

    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);
}
