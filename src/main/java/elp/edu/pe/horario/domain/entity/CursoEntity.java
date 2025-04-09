package elp.edu.pe.horario.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CursoEntity {

    private UUID id;
    private String nombre;
    private Integer horasSemanales;
    private String tipo;

    //relacion con unidad academica aqui

}
