package elp.edu.pe.horario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "asignacion_horario")
public class AsignacionHorarioEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version = 1L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_seccion_id", nullable = false)
    private CursoSeccionDocenteEntity cursoSeccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id")
    private AulaEntity aula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bloque_horario_id")
    private BloqueHorarioEntity bloqueHorario;

}
