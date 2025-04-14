package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.RestriccionDocenteJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RestriccionDocenteRepositoryImpl implements RestriccionDocenteRepository {

    private final RestriccionDocenteJpaRepository restriccionDocenteJpaRepository;

    public RestriccionDocenteRepositoryImpl(RestriccionDocenteJpaRepository restriccionDocenteJpaRepository) {
        this.restriccionDocenteJpaRepository = restriccionDocenteJpaRepository;
    }

    @Override
    public List<RestriccionDocente> findAll() {
        return List.of();
    }

    @Override
    public RestriccionDocente save(RestriccionDocente restriccionDocente) {
        return null;
    }

    @Override
    public RestriccionDocente findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
