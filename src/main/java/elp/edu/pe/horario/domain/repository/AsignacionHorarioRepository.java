package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.AsignacionHorario;

import java.util.List;
import java.util.UUID;

public interface AsignacionHorarioRepository {

    void deleteAllInBatch();

    AsignacionHorario save(AsignacionHorario asignacionHorario);

    List<AsignacionHorario> findBySeccionId(UUID seccionId);
    List<AsignacionHorario> findByDocenteId(UUID docenteId);
    List<AsignacionHorario> findByPeriodoId(UUID periodoId);

    boolean existeReferenciaDocente(UUID id);


}
