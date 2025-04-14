package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Seccion;

import java.util.List;

public interface SeccionRepository {

    List<Seccion> findAll();
    Seccion save(Seccion seccion);
    Seccion findById(Long id);
    void deleteById(Long id);
}
