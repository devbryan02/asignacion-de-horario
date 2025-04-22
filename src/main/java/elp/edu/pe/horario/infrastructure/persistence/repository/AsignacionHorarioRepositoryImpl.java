package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;

import elp.edu.pe.horario.infrastructure.mapper.AsignacionMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.AsignacionHorarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AsignacionHorarioRepositoryImpl implements AsignacionHorarioRepository {

    private final AsignacionHorarioJpaRepository jpaRepository;
    private final AsignacionMapper mapper;

    public AsignacionHorarioRepositoryImpl(AsignacionHorarioJpaRepository jpaRepository, AsignacionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public AsignacionHorario save(AsignacionHorario asignacionHorario) {
        AsignacionHorarioEntity entity = mapper.toEntity(asignacionHorario);
        AsignacionHorarioEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<AsignacionHorario> findAll() {
        return List.of();
    }

    @Override
    public Optional<AsignacionHorario> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<AsignacionHorario> saveAll(List<AsignacionHorario> asignaciones) {
        List<AsignacionHorarioEntity> entities = asignaciones.stream()
                .map(mapper::toEntity)
                .toList();
        List<AsignacionHorarioEntity> savedEntities = jpaRepository.saveAll(entities);
        return savedEntities.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AsignacionHorario> findByDocenteId(UUID docenteId) {
        return jpaRepository.findByDocenteId(docenteId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
