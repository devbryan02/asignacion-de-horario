package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Aula;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AulaRepository {

    Optional<Aula> findById(UUID id);
    List<Aula> findAll();
    Aula save(Aula aula);
    void deleteById(UUID id);
    void update(Aula aula);
    boolean existeReferenciaEnAsignacionHorario(UUID id);
    List<Aula> findByPeriodoId(UUID periodoId);

}


