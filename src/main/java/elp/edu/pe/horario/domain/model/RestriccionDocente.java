package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class RestriccionDocente {

    private UUID id;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String tipoRestriccion; // "NO DISPONIBLE" o "DISPONIBLE"
    private Docente docente;

    public RestriccionDocente(UUID id, String diaSemana, String horaInicio, String horaFin, String tipoRestriccion, Docente docente) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoRestriccion = tipoRestriccion;
        this.docente = docente;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(String tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }
}
