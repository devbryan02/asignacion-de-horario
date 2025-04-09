package elp.edu.pe.horario.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PeriodoEntity {

    private UUID id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
