package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;

import elp.edu.pe.horario.infrastructure.mapper.AsignacionMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.AsignacionHorarioJpaRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public class AsignacionHorarioRepositoryImpl implements AsignacionHorarioRepository {

    private final AsignacionHorarioJpaRepository jpaRepository;
    private final AsignacionMapper mapper;
    private final EntityManager entityManager;
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AsignacionHorarioRepositoryImpl.class);

    public AsignacionHorarioRepositoryImpl(AsignacionHorarioJpaRepository jpaRepository, AsignacionMapper mapper, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
    }

    @Override
    public List<AsignacionHorario> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }

    @Override
    public AsignacionHorario save(AsignacionHorario asignacionHorario) {
        AsignacionHorarioEntity entity = mapper.toEntity(asignacionHorario);
        entity = entityManager.merge(entity);
        return mapper.toDomain(entity);
    }


    @Override
    public Optional<AsignacionHorario> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

}
