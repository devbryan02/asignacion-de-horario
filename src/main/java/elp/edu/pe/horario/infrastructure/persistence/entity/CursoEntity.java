package elp.edu.pe.horario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cursos")
public class CursoEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private Integer horasSemanales;
    private String tipo;

    @ManyToOne(targetEntity = UnidadEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "unidad_id", nullable = false)
    private UnidadEntity unidad;

    @ManyToMany
    @JoinTable(
            name = "materia_seccion",
            joinColumns = @JoinColumn(name = "materia_id"),
            inverseJoinColumns = @JoinColumn(name = "seccion_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"materia_id", "seccion_id"})
    )
    private List<SeccionEntity> secciones = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AsignacionHorarioEntity> asignaciones = new ArrayList<>();

}
