package elp.edu.pe.horario.infrastructure.persistence.entity;

import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.Turno;
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
    @Enumerated(EnumType.STRING)
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    @Enumerated(EnumType.STRING)
    private Turno turno;

    @OneToMany(mappedBy = "bloqueHorario", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<AsignacionHorarioEntity> asignaciones = new ArrayList<>();

}
