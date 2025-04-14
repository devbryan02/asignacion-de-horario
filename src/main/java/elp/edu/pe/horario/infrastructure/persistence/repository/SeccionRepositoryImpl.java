package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.SeccionJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class SeccionRepositoryImpl implements SeccionRepository {

    private final SeccionJpaRepository seccionJpaRepository;

    public SeccionRepositoryImpl(SeccionJpaRepository seccionJpaRepository) {
        this.seccionJpaRepository = seccionJpaRepository;
    }

    @Override
    public List<Seccion> findAll() {
        return List.of();
    }

    @Override
    public Seccion save(Seccion seccion) {
        return null;
    }

    @Override
    public Seccion findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
