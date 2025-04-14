package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.BloqueHorario;

import java.util.List;

public interface BloqueHorarioRepository {

    BloqueHorario save(BloqueHorario bloqueHorarioEntity);

    BloqueHorario findById(Long id);

    void deleteById(Long id);

    List<BloqueHorario> findAll();

    List<BloqueHorario> findByAsignacionId(Long asignacionId);
}
