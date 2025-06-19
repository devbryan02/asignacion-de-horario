package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import elp.edu.pe.horario.domain.repository.CursoSeccionDocenteRepository;
import elp.edu.pe.horario.infrastructure.mapper.CursoSeccionDocenteMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.CursoSeccionDocenteEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.CursoSeccionDocenteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CursoSeccionDocenteRepositoryImpl implements CursoSeccionDocenteRepository {

    private final CursoSeccionDocenteJpaRepository jpaRepository;
    private final CursoSeccionDocenteMapper mapper;

    public CursoSeccionDocenteRepositoryImpl(CursoSeccionDocenteJpaRepository jpaRepository, CursoSeccionDocenteMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CursoSeccionDocente> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<CursoSeccionDocente> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public CursoSeccionDocente save(CursoSeccionDocente curso) {
        CursoSeccionDocenteEntity entity = mapper.toEntity(curso);
        CursoSeccionDocenteEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity) ;
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<CursoSeccionDocente> cursoSecciones) {
        jpaRepository.saveAllAndFlush(cursoSecciones.stream().map(mapper::toEntity).toList());
    }

    @Override
    public boolean existsByCursoIdAndSeccionIdAndDocenteId(UUID cursoId, UUID seccionId, UUID docenteId) {
        return jpaRepository.existsByCursoIdAndSeccionIdAndDocenteId(cursoId, seccionId, docenteId);
    }

    @Override
    public boolean existsByCursoIdAndSeccionId(UUID cursoId, UUID seccionId) {
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
