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
public class SeccionEntity {

    private UUID id;
    private String nombre;

    //relacion con periodo academico aqui

    //relacion con curso aqui
}
