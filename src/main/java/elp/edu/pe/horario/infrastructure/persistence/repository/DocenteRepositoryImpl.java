package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.infrastructure.mapper.DocenteMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.DocenteJpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DocenteRepositoryImpl implements DocenteRepository {

    private final DocenteJpaRepository jpaRepository;
    private final DocenteMapper mapper;

    public DocenteRepositoryImpl(DocenteJpaRepository jpaRepository, DocenteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Docente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Docente> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Docente save(Docente docente) {
        DocenteEntity entity = mapper.toEntity(docente);
        DocenteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
