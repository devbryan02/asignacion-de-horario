package elp.edu.pe.horario.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "periodos")
public class PeriodoEntity {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "periodo", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<SeccionEntity> secciones = new ArrayList<>();
}
