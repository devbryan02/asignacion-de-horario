package elp.edu.pe.horario.infrastructure.persistence.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;

import elp.edu.pe.horario.infrastructure.persistence.jpa.AsignacionHorarioJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AsignacionHorarioRepositoryImpl implements AsignacionHorarioRepository {

    private final AsignacionHorarioJpaRepository asignacionHorarioJpaRepository;

    public AsignacionHorarioRepositoryImpl(
            AsignacionHorarioJpaRepository asignacionHorarioJpaRepository) {
        this.asignacionHorarioJpaRepository = asignacionHorarioJpaRepository;
    }

    @Override
    public AsignacionHorario save(AsignacionHorario asignacionHorario) {
        return asignacionHorarioJpaRepository.save(asignacionHorario);
    }

    @Override
    public AsignacionHorario findById(Long id) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
