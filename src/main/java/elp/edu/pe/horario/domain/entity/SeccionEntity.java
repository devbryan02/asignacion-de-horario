package elp.edu.pe.horario.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "secciones")
public class SeccionEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;

    @ManyToOne(targetEntity = PeriodoEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_id", nullable = false)
    private PeriodoEntity periodo;

    @ManyToMany(mappedBy = "secciones")
    private List<CursoEntity> cursos = new ArrayList<>();
}
