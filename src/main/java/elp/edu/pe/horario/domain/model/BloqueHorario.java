package elp.edu.pe.horario.domain.model;

import java.time.LocalTime;
import java.util.UUID;

public class BloqueHorario {

    private UUID id;
    private Integer diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String Turno;

    public BloqueHorario(
            UUID id,
            Integer diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin,
            String turno
    ) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        Turno = turno;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getTurno() {
        return Turno;
    }

    public void setTurno(String turno) {
        Turno = turno;
    }
}
