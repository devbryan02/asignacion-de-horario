package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.CursoSeccion;
import elp.edu.pe.horario.domain.repository.CursoSeccionRepository;
import elp.edu.pe.horario.infrastructure.mapper.CursoSeccionMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.CursoSeccionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CursoSeccionRepositoryImpl implements CursoSeccionRepository {

    private final CursoSeccionJpaRepository jpaRepository;
    private final CursoSeccionMapper mapper;

    public CursoSeccionRepositoryImpl(CursoSeccionJpaRepository jpaRepository, CursoSeccionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CursoSeccion> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<CursoSeccion> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public CursoSeccion save(CursoSeccion curso) {
        CursoSeccionEntity entity = mapper.toEntity(curso);
        CursoSeccionEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity) ;
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<CursoSeccion> cursoSecciones) {
        jpaRepository.saveAllAndFlush(cursoSecciones.stream().map(mapper::toEntity).toList());
    }

    @Override
    public boolean existsByCursoAndSeccion(UUID cursoId, UUID seccionId) {
        return jpaRepository.existsByCursoIdAndSeccionId(cursoId, seccionId);
    }

    @Override
    public boolean existsByCursoId(UUID cursoId) {
        return jpaRepository.existsByCursoId(cursoId) ;
    }

    @Override
    public boolean existsBySeccionId(UUID seccionId) {
        return jpaRepository.existsBySeccionId(seccionId) ;
    }
}
