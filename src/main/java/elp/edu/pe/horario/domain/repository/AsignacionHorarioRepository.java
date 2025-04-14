package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;

public interface AsignacionHorarioRepository {

    AsignacionHorario save(AsignacionHorario asignacionHorario);
    AsignacionHorario findById(Long id);
    void deleteById(Long id);

}
