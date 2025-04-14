package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.CursoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CursoRepositoryImpl implements CursoRepository {

    private final CursoJpaRepository cursoJpaRepository;

    @Override
    public List<Curso> findAll() {
        return List.of();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Curso save(Curso curso) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
