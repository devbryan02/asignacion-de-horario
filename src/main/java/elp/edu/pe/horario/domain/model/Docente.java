package elp.edu.pe.horario.domain.model;

import java.util.List;
import java.util.UUID;

public class Docente {

    private UUID id;
    private String nombre;
    private Integer horasContratadas;
    private Integer horasMaximasPorDia;

    private List<RestriccionDocente> restricciones;
    private List<UnidadAcademica> unidadesAcademicas;

    public Docente(UUID id, String nombre, Integer horasContratadas, Integer horasMaximasPorDia) {
        this.id = id;
        this.nombre = nombre;
        this.horasContratadas = horasContratadas;
        this.horasMaximasPorDia = horasMaximasPorDia;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getHorasContratadas() {
        return horasContratadas;
    }

    public void setHorasContratadas(Integer horasContratadas) {
        this.horasContratadas = horasContratadas;
    }

    public Integer getHorasMaximasPorDia() {
        return horasMaximasPorDia;
    }

    public void setHorasMaximasPorDia(Integer horasMaximasPorDia) {
        this.horasMaximasPorDia = horasMaximasPorDia;
    }

    public List<RestriccionDocente> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<RestriccionDocente> restricciones) {
        this.restricciones = restricciones;
    }

    public List<UnidadAcademica> getUnidadesAcademicas() {
        return unidadesAcademicas;
    }

    public void setUnidadesAcademicas(List<UnidadAcademica> unidadesAcademicas) {
        this.unidadesAcademicas = unidadesAcademicas;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horasContratadas=" + horasContratadas +
                ", horasMaximasPorDia=" + horasMaximasPorDia +
                ", restricciones=" + restricciones +
                ", unidadesAcademicas=" + unidadesAcademicas +
                '}';
    }
}