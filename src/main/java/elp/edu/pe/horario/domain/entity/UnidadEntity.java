package elp.edu.pe.horario.domain.entity;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "unidad_academica")
public class UnidadEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;

    @OneToMany(mappedBy = "unidad", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<CursoEntity> cursos = new ArrayList<>();

    @ManyToMany(mappedBy = "unidades")
    private List<DocenteEntity> docentes = new ArrayList<>();

}
