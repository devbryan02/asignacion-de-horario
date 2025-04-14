package elp.edu.pe.horario.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bloque_horario")
public class BloqueHorarioEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String Turno;

    @OneToMany(mappedBy = "bloqueHorario", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AsignacionHorarioEntity> asignaciones = new ArrayList<>();

}
