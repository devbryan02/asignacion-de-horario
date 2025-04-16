package elp.edu.pe.horario.domain.model;

import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.enums.TipoRestriccion;

import java.time.LocalTime;
import java.util.UUID;

public class RestriccionDocente {

    private UUID id;
    private DiaSemana diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private TipoRestriccion tipoRestriccion; // "NO DISPONIBLE" o "DISPONIBLE"
    private Docente docente;

    public RestriccionDocente(UUID id, DiaSemana diaSemana, LocalTime horaFin, LocalTime horaInicio, TipoRestriccion tipoRestriccion, Docente docente) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaFin = horaFin;
        this.horaInicio = horaInicio;
        this.tipoRestriccion = tipoRestriccion;
        this.docente = docente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(DiaSemana diaSemana) {
        this.diaSemana = diaSemana;
    }
}
