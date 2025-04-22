package elp.edu.pe.horario.domain.model;

import java.util.List;
import java.util.UUID;

public class Docente {

    private UUID id;
    private String nombre;
    private Integer horasContratadas;
    private List<RestriccionDocente> restricciones;
    private List<UnidadAcademica> unidadesAcademicas;

    public Docente(UUID id, String nombre, Integer horasContratadas) {
        this.id = id;
        this.nombre = nombre;
        this.horasContratadas = horasContratadas;
    }

    public List<UnidadAcademica> getUnidadesAcademicas() {
        return unidadesAcademicas;
    }

    public void setUnidadesAcademicas(List<UnidadAcademica> unidadesAcademicas) {
        this.unidadesAcademicas = unidadesAcademicas;
    }

    public List<RestriccionDocente> getRestricciones() {
        return restricciones;
    }

    public void setRestricciones(List<RestriccionDocente> restricciones) {
        this.restricciones = restricciones;
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

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horasContratadas=" + horasContratadas +
                '}';
    }
}