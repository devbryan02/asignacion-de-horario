package elp.edu.pe.horario.infrastructure.persistence.jpa;

import elp.edu.pe.horario.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);

}

