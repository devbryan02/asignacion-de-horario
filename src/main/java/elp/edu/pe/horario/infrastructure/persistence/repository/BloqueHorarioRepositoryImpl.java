package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import elp.edu.pe.horario.infrastructure.mapper.BloqueHorarioMapper;
import elp.edu.pe.horario.infrastructure.persistence.entity.BloqueHorarioEntity;
import elp.edu.pe.horario.infrastructure.persistence.jpa.BloqueHorarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BloqueHorarioRepositoryImpl implements BloqueHorarioRepository {

    private final BloqueHorarioJpaRepository jpaRepository;
    private final BloqueHorarioMapper mapper;

    @Override
    public BloqueHorario save(BloqueHorario bloqueHorarioEntity) {
        BloqueHorarioEntity entity = mapper.toEntity(bloqueHorarioEntity);
        BloqueHorarioEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<BloqueHorario> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<BloqueHorario> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void update(BloqueHorario bloqueHorario) {
        jpaRepository.save(mapper.toEntity(bloqueHorario));
    }

    @Override
    public boolean existeReferenciaEnAsignacionHorario(UUID id) {
        return jpaRepository.existeReferenciaEnAsignacionHorario(id);
    }

    @Override
    public List<BloqueHorario> findByPeriodoId(UUID periodoId) {
        return jpaRepository.findByPeriodoId(periodoId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

}
