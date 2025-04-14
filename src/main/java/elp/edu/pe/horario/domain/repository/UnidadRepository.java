package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.UnidadAcademica;

import java.util.List;
import java.util.Optional;

public interface UnidadRepository {

    List<UnidadAcademica> findAll();
    Optional<UnidadAcademica> findById(Long id);
    UnidadAcademica save(UnidadAcademica unidad);
    void deleteById(Long id);
}
