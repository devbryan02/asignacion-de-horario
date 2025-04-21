package elp.edu.pe.horario.infrastructure.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import elp.edu.pe.horario.domain.enums.ModoClase;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "curso_seccion")
public class CursoSeccionEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private CursoEntity curso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seccion_id", nullable = false)
    private SeccionEntity seccion;

    @Enumerated(EnumType.STRING)
    private ModoClase modo;
}
