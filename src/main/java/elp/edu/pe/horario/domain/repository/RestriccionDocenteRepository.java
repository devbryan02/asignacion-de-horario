package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.RestriccionDocente;

import java.util.List;

public interface RestriccionDocenteRepository {

    List<RestriccionDocente> findAll();
    RestriccionDocente save(RestriccionDocente restriccionDocente);
    RestriccionDocente findById(Long id);
    void deleteById(Long id);

}
