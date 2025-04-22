package elp.edu.pe.horario.domain.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.UUID;

@PlanningEntity
public class AsignacionHorario {

    @PlanningId
    private UUID id;

    private Docente docente;
    private CursoSeccion cursoSeccion;

    @PlanningVariable(valueRangeProviderRefs = "aulaRange")
    private Aula aula;

    @PlanningVariable(valueRangeProviderRefs = "bloqueHorarioRange")
    private BloqueHorario bloqueHorario;

    public AsignacionHorario() {
    }

    public AsignacionHorario(UUID id, Docente docente, CursoSeccion cursoSeccion, Aula aula, BloqueHorario bloqueHorario) {
        this.id = id;
        this.docente = docente;
        this.cursoSeccion = cursoSeccion;
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

    public CursoSeccion getCursoSeccion() {
        return cursoSeccion;
    }

    public void setCursoSeccion(CursoSeccion cursoSeccion) {
        this.cursoSeccion = cursoSeccion;
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

    @Override
    public String toString() {
        return "AsignacionHorario{" +
                "id=" + id +
                ", docente=" + docente +
                ", cursoSeccion=" + cursoSeccion +
                ", aula=" + aula +
                ", bloqueHorario=" + bloqueHorario +
                '}';
    }
}
