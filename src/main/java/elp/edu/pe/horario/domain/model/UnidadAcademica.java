package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class UnidadAcademica {

    private UUID id;
    private String nombre;

    public UnidadAcademica(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UnidadAcademica{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
