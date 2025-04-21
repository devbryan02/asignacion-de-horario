package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class Seccion {

    private UUID id;
    private String nombre;
    private PeriodoAcademico periodoAcademico;

    public Seccion(UUID id, String nombre, PeriodoAcademico periodoAcademico) {
        this.id = id;
        this.nombre = nombre;
        this.periodoAcademico = periodoAcademico;
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

    public PeriodoAcademico getPeriodo() {
        return periodoAcademico;
    }

    public void setPeriodo(PeriodoAcademico periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    @Override
    public String toString() {
        return "Seccion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", periodoAcademico=" + periodoAcademico +
                '}';
    }
}
