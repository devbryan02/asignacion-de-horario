package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UnidadJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UnidadRepositoryImpl implements UnidadRepository {

    private final UnidadJpaRepository unidadJpaRepository;

    public UnidadRepositoryImpl(UnidadJpaRepository unidadJpaRepository) {
        this.unidadJpaRepository = unidadJpaRepository;
    }

    @Override
    public List<UnidadAcademica> findAll() {
        return List.of();
    }

    @Override
    public Optional<UnidadAcademica> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UnidadAcademica save(UnidadAcademica unidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
