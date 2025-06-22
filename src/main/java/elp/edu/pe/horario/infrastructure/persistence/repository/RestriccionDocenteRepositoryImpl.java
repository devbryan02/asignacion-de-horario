package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;
import elp.edu.pe.horario.infrastructure.mapper.RestriccionDocenteMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.RestriccionDocenteEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.RestriccionDocenteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class RestriccionDocenteRepositoryImpl implements RestriccionDocenteRepository {

    private final RestriccionDocenteJpaRepository jpaRepository;
    private final RestriccionDocenteMapper mapper;

    public RestriccionDocenteRepositoryImpl(RestriccionDocenteJpaRepository jpaRepository, RestriccionDocenteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RestriccionDocente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public RestriccionDocente save(RestriccionDocente restriccionDocente) {
        RestriccionDocenteEntity entity = mapper.toEntity(restriccionDocente);
        RestriccionDocenteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<RestriccionDocente> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<RestriccionDocente> findAllByDocenteId(UUID docenteId) {
        return jpaRepository.findAllByDocenteId(docenteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existsByDocenteId(UUID docenteId) {
        return jpaRepository.existsByDocenteId(docenteId);
    }

    @Override
    public void saveAll(List<RestriccionDocente> restricciones) {
        List<RestriccionDocenteEntity> entities = restricciones.stream()
                .map(mapper::toEntity)
                .toList();
        jpaRepository.saveAll(entities);
    }
}