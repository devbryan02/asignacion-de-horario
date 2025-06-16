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

    @ManyToMany
    @JoinTable(
            name = "curso_unidad_academica",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "unidad_academica_id")
    )
    private List<UnidadEntity> unidades = new ArrayList<>();

    @OneToMany(mappedBy = "curso")
    private List<CursoSeccionDocenteEntity> cursoSecciones = new ArrayList<>();

}
