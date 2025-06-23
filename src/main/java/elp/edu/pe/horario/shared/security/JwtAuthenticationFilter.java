package elp.edu.pe.horario.shared.security;

import elp.edu.pe.horario.application.usecase.user.LoadByUsernameUserCase;
import elp.edu.pe.horario.shared.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter {

    private static final Logger  log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private LoadByUsernameUserCase loadByUsernameUserCase;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHearder = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        // si tenemos el header de Authorization y empieza con Bearer
        if(authHearder != null && authHearder.startsWith("Bearer ")) {
            jwt = authHearder.substring(7);
            try{
                username = jwtUtils.extracUsername(jwt);
            }catch (Exception e){
                log.warn("Token inválido: {}", e.getMessage());
            }
        }

        // si tenemos el usuario y aun on hay auth en el contexto
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            var userDetails = loadByUsernameUserCase.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(jwt, userDetails.getUsername())) {
                // extraemos el rol desde el token
                String role = jwtUtils.extractClaim(jwt, claims -> claims.get("role", String.class));
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continuar con la cadena de filtros
        filterChain.doFilter(request,response);
    }

}
