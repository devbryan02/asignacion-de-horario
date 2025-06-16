package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.infrastructure.mapper.AsignacionMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.AsignacionHorarioEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.AsignacionHorarioJpaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AsignacionHorarioRepositoryImpl implements AsignacionHorarioRepository {

    private final AsignacionHorarioJpaRepository jpaRepository;
    private final AsignacionMapper mapper;
    private final EntityManager entityManager;

    public AsignacionHorarioRepositoryImpl(AsignacionHorarioJpaRepository jpaRepository, AsignacionMapper mapper, EntityManager entityManager) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
        this.entityManager = entityManager;
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
    public List<AsignacionHorario> findBySeccionId(UUID seccionId) {
        return jpaRepository.findByCursoSeccion_Seccion_Id(seccionId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AsignacionHorario> findByDocenteId(UUID docenteId) {
        return jpaRepository.findByCursoSeccion_Docente_Id(docenteId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AsignacionHorario> findByPeriodoId(UUID periodoId) {
        return jpaRepository.findByCursoSeccion_Seccion_Periodo_Id(periodoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public boolean existeReferenciaDocente(UUID id) {
        return jpaRepository.existsByCursoSeccion_Docente_Id(id);
    }

}
