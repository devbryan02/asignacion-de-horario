package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.UnidadAcademica;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnidadRepository {

    List<UnidadAcademica> findAll();
    Optional<UnidadAcademica> findById(UUID id);
    UnidadAcademica save(UnidadAcademica unidad);
    void deleteById(UUID id);
    List<UnidadAcademica> findAllById(List<UUID> ids);
}
