package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.Curso;
import elp.edu.pe.horario.domain.repository.CursoRepository;
import elp.edu.pe.horario.infrastructure.mapper.CursoMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.CursoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CursoRepositoryImpl implements CursoRepository {

    private final CursoJpaRepository jpaRepository;
    private final CursoMapper mapper;

    public CursoRepositoryImpl(CursoJpaRepository jpaRepository, CursoMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Curso> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Curso> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Curso save(Curso curso) {
        CursoEntity entity = mapper.toEntity(curso);
        // Asegúrate de que la lista de secciones no sea null
        if (entity.getUnidades() == null) {
            entity.setUnidades(new ArrayList<>());
        }
        CursoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void update(Curso curso) {
        jpaRepository.save(mapper.toEntity(curso));
    }

    @Override
    public boolean existeReferenciaEnCursoSeccion(UUID id) {
        return jpaRepository.existeReferenciaEnCursoSeccion(id);
    }
}
