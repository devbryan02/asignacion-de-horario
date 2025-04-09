package elp.edu.pe.horario.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BloqueHorarioEntity {

    private UUID id;
    private Integer diaSemana; // 0= Lunes, 1= Martes, 2= Miercoles, 3= Jueves, 4= Viernes, 5= Sabado
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String Turno; // Mañana, Tarde, Noche

}
