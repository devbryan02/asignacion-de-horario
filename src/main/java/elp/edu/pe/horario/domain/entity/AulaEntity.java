package elp.edu.pe.horario.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AulaEntity {

    private UUID id;
    private String nombre;
    private Integer capacidad;
    private String tipo;

}
