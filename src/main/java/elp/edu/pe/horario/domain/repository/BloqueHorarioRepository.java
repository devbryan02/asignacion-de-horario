package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.BloqueHorario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BloqueHorarioRepository {

    BloqueHorario save(BloqueHorario bloqueHorario);

    Optional<BloqueHorario> findById(UUID id);

    void deleteById(UUID id);

    List<BloqueHorario> findAll();
    void update(BloqueHorario bloqueHorario);

    boolean existeReferenciaEnAsignacionHorario(UUID id);

}
