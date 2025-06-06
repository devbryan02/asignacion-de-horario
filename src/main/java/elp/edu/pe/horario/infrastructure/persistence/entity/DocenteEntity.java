package elp.edu.pe.horario.infrastructure.persistence.entity;

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
@Table(name = "docentes")
public class DocenteEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private Integer horasContratadas;
    private Integer horasMaximasPorDia;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "docente_unidad",
            joinColumns = @JoinColumn(name = "docente_id"),
            inverseJoinColumns = @JoinColumn(name = "unidad_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"docente_id", "unidad_id"})
    )
    private List<UnidadEntity> unidades = new ArrayList<>();

    @OneToMany(mappedBy = "docente", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AsignacionHorarioEntity> asignaciones = new ArrayList<>();

    @OneToMany(mappedBy = "docente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RestriccionDocenteEntity> restricciones = new ArrayList<>();

}
