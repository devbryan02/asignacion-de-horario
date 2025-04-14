package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class Docente {

    private UUID id;
    private String nombre;
    private Integer horasContradadas;

    public Docente(UUID id, String nombre, Integer horasContradadas) {
        this.id = id;
        this.nombre = nombre;
        this.horasContradadas = horasContradadas;
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

    public Integer getHorasContradadas() {
        return horasContradadas;
    }

    public void setHorasContradadas(Integer horasContradadas) {
        this.horasContradadas = horasContradadas;
    }
}