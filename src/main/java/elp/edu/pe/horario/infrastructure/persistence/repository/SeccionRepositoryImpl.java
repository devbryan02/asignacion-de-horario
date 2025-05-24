package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.repository.SeccionRepository;
import elp.edu.pe.horario.infrastructure.mapper.SeccionMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.SeccionEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.SeccionJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class SeccionRepositoryImpl implements SeccionRepository {

    private final SeccionJpaRepository jpaRepository;
    private final SeccionMapper mapper;

    public SeccionRepositoryImpl(SeccionJpaRepository jpaRepository, SeccionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Seccion> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Seccion save(Seccion seccion) {
        SeccionEntity entity = mapper.toEntity(seccion);
        SeccionEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Seccion> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void update(Seccion seccion) {
        jpaRepository.save(mapper.toEntity(seccion));
    }
}