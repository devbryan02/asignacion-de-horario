package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import elp.edu.pe.horario.infrastructure.mapper.PeriodoMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.PeriodoEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.PeriodoJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PeriodoRepositoryImpl implements PeriodoRepository {

    private final PeriodoJpaRepository jpaRepository;
    private final PeriodoMapper mapper;

    public PeriodoRepositoryImpl(PeriodoJpaRepository jpaRepository, PeriodoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<PeriodoAcademico> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<PeriodoAcademico> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PeriodoAcademico save(PeriodoAcademico periodo) {
        PeriodoEntity entity = mapper.toEntity(periodo);
        PeriodoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
