package elp.edu.pe.horario.domain.model;

import elp.edu.pe.horario.domain.enums.ModoClase;

import java.util.UUID;

public class CursoSeccion {

    private UUID id;
    private Curso curso;
    private Seccion seccion;
    private ModoClase modo;

    public CursoSeccion(UUID id, Curso curso, Seccion seccion, ModoClase modo) {
        this.id = id;
        this.curso = curso;
        this.seccion = seccion;
        this.modo = modo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }

    public ModoClase getModo() {
        return modo;
    }

    public void setModo(ModoClase modo) {
        this.modo = modo;
    }

    @Override
    public String toString() {
        return "CursoSeccion{" +
                "id=" + id +
                ", curso=" + curso +
                ", seccion=" + seccion +
                ", modo=" + modo +
                '}';
    }
}
