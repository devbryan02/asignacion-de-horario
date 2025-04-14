package elp.edu.pe.horario.domain.repository;

import elp.edu.pe.horario.domain.model.Aula;

import java.util.List;

public interface AulaRepository {

    Aula findById(Long id);
    List<Aula> findAll();
    Aula save(Aula aula);
    void deleteById(Long id);

}
