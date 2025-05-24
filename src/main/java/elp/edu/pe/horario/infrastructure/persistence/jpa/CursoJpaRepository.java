package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.CursoEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CursoJpaRepository extends JpaRepository<CursoEntity, UUID> {

    @Query("SELECT COUNT(c) > 0 FROM CursoSeccionEntity c WHERE c.curso.id = :cursoId")
    boolean existeReferenciaEnCursoSeccion(@Param("cursoId") UUID cursoId);
}
