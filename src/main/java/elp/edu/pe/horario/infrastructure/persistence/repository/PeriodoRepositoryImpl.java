package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.PeriodoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PeriodoRepositoryImpl implements PeriodoRepository {

    private final PeriodoJpaRepository periodoJpaRepository;

    public PeriodoRepositoryImpl(PeriodoJpaRepository periodoJpaRepository) {
        this.periodoJpaRepository = periodoJpaRepository;
    }

    @Override
    public List<PeriodoAcademico> findAll() {
        return List.of();
    }

    @Override
    public PeriodoAcademico findById(Long id) {
        return null;
    }

    @Override
    public PeriodoAcademico save(PeriodoAcademico docente) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
