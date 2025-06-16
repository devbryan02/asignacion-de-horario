package elp.edu.pe.horario.domain.model;

import elp.edu.pe.horario.domain.enums.ModoClase;
import lombok.Getter;

import java.util.UUID;

public class CursoSeccionDocente {

    private UUID id;
    private Curso curso;
    private Seccion seccion;
    private Docente docente;
    private ModoClase modo;

    public CursoSeccionDocente(UUID id, Curso curso, Seccion seccion, Docente docente, ModoClase modo) {
        this.id = id;
        this.curso = curso;
        this.seccion = seccion;
        this.docente = docente;
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

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
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
                ", docente=" + docente +
                ", modo=" + modo +
                '}';
    }
}
