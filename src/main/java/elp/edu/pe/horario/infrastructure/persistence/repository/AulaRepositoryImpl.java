package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.AulaJpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AulaRepositoryImpl implements AulaRepository {

    private final AulaJpaRepository jpaRepository;

    public AulaRepositoryImpl(AulaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Aula findById(Long id) {
        return null;
    }

    @Override
    public List<Aula> findAll() {
        return List.of();
    }

    @Override
    public Aula save(Aula aula) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
