package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.PeriodoAcademico;

import java.util.List;

public interface PeriodoRepository {

    List<PeriodoAcademico> findAll();
    PeriodoAcademico findById(Long id);
    PeriodoAcademico save(PeriodoAcademico docente);
    void deleteById(Long id);


}
