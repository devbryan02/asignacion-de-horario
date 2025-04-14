package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AsignacionHorarioRepository {

    AsignacionHorario save(AsignacionHorario asignacionHorario);
    Optional<AsignacionHorario> findById(UUID id);
    void deleteById(UUID id);
    List<AsignacionHorario> findAll();

}
