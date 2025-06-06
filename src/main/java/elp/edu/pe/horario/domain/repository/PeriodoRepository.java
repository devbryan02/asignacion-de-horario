package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PeriodoRepository {

    List<PeriodoAcademico> findAll();
    Optional<PeriodoAcademico> findById(UUID id);
    PeriodoAcademico save(PeriodoAcademico periodo);
    void deleteById(UUID id);
    void actualizar(PeriodoAcademico periodo);

}
