package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class Curso {

    private UUID id;
    private String nombre;
    private Integer horasSemanales;
    private String tipo;
    private UnidadAcademica unidad;

    public Curso(UUID id, String nombre, Integer horasSemanales, String tipo, UnidadAcademica unidad) {
        this.id = id;
        this.nombre = nombre;
        this.horasSemanales = horasSemanales;
        this.tipo = tipo;
        this.unidad = unidad;
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

    public Integer getHorasSemanales() {
        return horasSemanales;
    }

    public void setHorasSemanales(Integer horasSemanales) {
        this.horasSemanales = horasSemanales;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UnidadAcademica getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadAcademica unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", horasSemanales=" + horasSemanales +
                ", tipo='" + tipo + '\'' +
                ", unidad=" + unidad +
                '}';
    }
}
