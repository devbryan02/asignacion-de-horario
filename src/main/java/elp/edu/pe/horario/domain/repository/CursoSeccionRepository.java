package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.CursoSeccion;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoSeccionRepository {

    List<CursoSeccion> findAll();
    Optional<CursoSeccion> findById(UUID id);
    CursoSeccion save(CursoSeccion curso);
    void deleteById(UUID id);
}
