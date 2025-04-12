package elp.edu.pe.horario.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "restriccion_docente")
public class RestriccionDocente {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String diaSeman;
    private String horaInicio;
    private String horaFin;
    private String tipoRestriccion; // "NO DISPONIBLE" o "DISPONIBLE"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "docente_id", nullable = false)
    private DocenteEntity docente;


}
