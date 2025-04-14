package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.repository.BloqueHorarioRepository;
import elp.edu.pe.horario.infrastructure.persistence.jpa.BloqueHorarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BloqueHorarioRepositoryImpl implements BloqueHorarioRepository {

    private final BloqueHorarioJpaRepository jpaRepository;

    @Override
    public BloqueHorario save(BloqueHorario bloqueHorarioEntity) {
        return null;
    }

    @Override
    public BloqueHorario findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BloqueHorario> findAll() {
        return List.of();
    }

    @Override
    public List<BloqueHorario> findByAsignacionId(Long asignacionId) {
        return List.of();
    }
}
