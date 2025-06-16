package elp.edu.pe.horario.domain.model;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

import java.util.UUID;

@PlanningEntity
public class AsignacionHorario {

    @PlanningId
    private UUID id;

    private CursoSeccionDocente cursoSeccionDocente;

    @PlanningVariable(valueRangeProviderRefs = "aulaRange")
    private Aula aula;

    @PlanningVariable(valueRangeProviderRefs = "bloqueHorarioRange")
    private BloqueHorario bloqueHorario;

    public AsignacionHorario() {
    }

    public AsignacionHorario(UUID id, CursoSeccionDocente cursoSeccionDocente, Aula aula, BloqueHorario bloqueHorario) {
        this.id = id;
        this.cursoSeccionDocente = cursoSeccionDocente;
        this.aula = aula;
        this.bloqueHorario = bloqueHorario;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public CursoSeccionDocente getCursoSeccionDocente() {
        return cursoSeccionDocente;
    }

    public void setCursoSeccionDocente(CursoSeccionDocente cursoSeccionDocente) {
        this.cursoSeccionDocente = cursoSeccionDocente;
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
                ", cursoSeccionDocente=" + cursoSeccionDocente +
                ", aula=" + aula +
                ", bloqueHorario=" + bloqueHorario +
                '}';
    }
}
