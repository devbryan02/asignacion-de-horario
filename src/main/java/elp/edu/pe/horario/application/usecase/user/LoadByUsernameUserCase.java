package elp.edu.pe.horario.application.usecase.user;

import elp.edu.pe.horario.infrastructure.persistence.jpa.UserJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoadByUsernameUserCase implements UserDetailsService {

    private final UserJpaRepository userJpaRepository;

    public LoadByUsernameUserCase(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userJpaRepository.findByUsername(username)
                .orElseThrow(() -> new
                        UsernameNotFoundException("Usuario no encontrado: " + username));
    }
}

