package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import elp.edu.pe.horario.infrastructure.mapper.UnidadMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.UnidadEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.UnidadJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UnidadRepositoryImpl implements UnidadRepository {

    private final UnidadJpaRepository jpaRepository;
    private final UnidadMapper mapper;

    public UnidadRepositoryImpl(UnidadJpaRepository jpaRepository, UnidadMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UnidadAcademica> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<UnidadAcademica> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public UnidadAcademica save(UnidadAcademica unidad) {
        UnidadEntity entity = mapper.toEntity(unidad);
        UnidadEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<UnidadAcademica> findAllById(List<UUID> ids) {
        return jpaRepository.findAllById(ids)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}