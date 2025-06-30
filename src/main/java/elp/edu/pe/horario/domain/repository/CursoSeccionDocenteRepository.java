package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.CursoSeccionDocente;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CursoSeccionDocenteRepository {

    List<CursoSeccionDocente> findAll();
    Optional<CursoSeccionDocente> findById(UUID id);
    CursoSeccionDocente save(CursoSeccionDocente curso);
    void deleteById(UUID id);
    void saveAll(List<CursoSeccionDocente> cursoSecciones);
    boolean existsByCursoIdAndSeccionIdAndDocenteId(UUID cursoId, UUID seccionId, UUID docenteId);
    boolean existsByCursoIdAndSeccionId(UUID cursoId, UUID seccionId);
    boolean existsByCursoId(UUID cursoId);
    boolean existsBySeccionId(UUID seccionId);
    long countByCursoIdAndDocenteId(UUID cursoId, UUID docenteId);

}
