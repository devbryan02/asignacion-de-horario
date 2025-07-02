package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Aula;
import elp.edu.pe.horario.domain.repository.AulaRepository;
import elp.edu.pe.horario.infrastructure.mapper.AulaMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.AulaEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.AulaJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AulaRepositoryImpl implements AulaRepository {

    private final AulaMapper mapper;
    private final AulaJpaRepository aulaJpaRepository;

    public AulaRepositoryImpl(AulaMapper mapper, AulaJpaRepository aulaJpaRepository) {
        this.mapper = mapper;
        this.aulaJpaRepository = aulaJpaRepository;
    }

    @Override
    public Optional<Aula> findById(UUID id) {
        return aulaJpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Aula> findAll() {
        return aulaJpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Aula save(Aula aula) {
        AulaEntity entity = mapper.toEntity(aula);
        AulaEntity savedEntity = aulaJpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        aulaJpaRepository.deleteById(id);
    }
    @Override
    public void update(Aula aula) {
        aulaJpaRepository.save(mapper.toEntity(aula));
    }

    @Override
    public boolean existeReferenciaEnAsignacionHorario(UUID id) {
        return aulaJpaRepository.existeReferenciaEnAsignacionHorario(id);
    }

    @Override
    public List<Aula> findByPeriodoId(UUID periodoId) {
        return aulaJpaRepository.findByPeriodoId(periodoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
