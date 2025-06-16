package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface CursoJpaRepository extends JpaRepository<CursoEntity, UUID> {

    @Query("SELECT COUNT(c) > 0 FROM CursoSeccionDocenteEntity c WHERE c.curso.id = :cursoId")
    boolean existeReferenciaEnCursoSeccion(@Param("cursoId") UUID cursoId);
}
