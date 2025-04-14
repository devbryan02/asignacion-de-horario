package elp.edu.pe.horario.domain.model;

import java.util.UUID;

public class AsignacionHorario {

    private UUID id;
    private Docente docente;
    private Curso curso;
    private Aula aula;
    private BloqueHorario bloqueHorario;

    public AsignacionHorario(
            UUID id,
            Docente docente,
            Curso curso,
            Aula aula,
            BloqueHorario bloqueHorario
    ) {
        this.id = id;
        this.docente = docente;
        this.curso = curso;
        this.aula = aula;
        this.bloqueHorario = bloqueHorario;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public BloqueHorario getBloqueHorario() {
        return bloqueHorario;
    }

    public void setBloqueHorario(BloqueHorario bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }
}
