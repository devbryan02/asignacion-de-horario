package elp.edu.pe.horario.domain.model;

import elp.edu.pe.horario.domain.enums.TipoAula;

import java.util.Objects;
import java.util.UUID;

public class Aula {

    private UUID id;
    private String nombre;
    private Integer capacidad;
    private TipoAula tipo;

    public Aula(UUID id, String nombre, Integer capacidad, TipoAula tipo) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipo = tipo;
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

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public TipoAula getTipo() {
        return tipo;
    }

    public void setTipo(TipoAula tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Aula{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", tipo=" + tipo +
                '}';
    }

    // metodo equals y hashCode es necesario para comparar objetos Aula
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aula aula = (Aula) o;
        return Objects.equals(id, aula.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
