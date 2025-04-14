package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.DocenteJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DocenteRepositoryImpl implements DocenteRepository {

    private final DocenteJpaRepository docenteJPaRepository;

    public DocenteRepositoryImpl(DocenteJpaRepository docenteJPaRepository) {
        this.docenteJPaRepository = docenteJPaRepository;
    }

    @Override
    public List<Docente> findAll() {
        return List.of();
    }

    @Override
    public Docente findById(Long id) {
        return null;
    }

    @Override
    public Docente save(Docente docente) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
