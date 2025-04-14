package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Docente;

import java.util.List;

public interface DocenteRepository {

    List<Docente> findAll();
    Docente findById(Long id);
    Docente save(Docente docente);
    void deleteById(Long id);
}
