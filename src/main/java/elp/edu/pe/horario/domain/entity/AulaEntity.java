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
@Table(name = "aulas")
public class AulaEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private Integer capacidad;
    private String tipo;

    @OneToMany(mappedBy = "aula", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AsignacionHorarioEntity> asignaciones = new ArrayList<>();

}
