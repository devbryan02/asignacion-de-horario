package elp.edu.pe.horario.domain.solver;

import elp.edu.pe.horario.domain.model.*;
import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningScore;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.ProblemFactCollectionProperty;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;

import java.util.List;

@PlanningSolution
public class HorarioSolucion {

    //Datos fijos para usar de referencia
    @ProblemFactCollectionProperty
    private List<Aula> aulaList;

    @ProblemFactCollectionProperty
    private List<BloqueHorario> bloqueHorarioList;

    @ProblemFactCollectionProperty
    private List<Docente> docenteList;

    @ProblemFactCollectionProperty
    private List<CursoSeccion> cursoSeccionList;

    // Entidad a planificar
    @PlanningEntityCollectionProperty
    private List<AsignacionHorario> asignacionHorarioList;

    //Score final
    @PlanningScore
    private HardSoftScore score;

    //construcctor vacio
    public HorarioSolucion() {
    }

    //constructor con todos los atributos
    public HorarioSolucion(List<Aula> aulaList, List<BloqueHorario> bloqueHorarioList, List<Docente> docenteList,
                           List<CursoSeccion> cursoSeccionList, List<AsignacionHorario> asignacionHorarioList) {
        this.aulaList = aulaList;
        this.bloqueHorarioList = bloqueHorarioList;
        this.docenteList = docenteList;
        this.cursoSeccionList = cursoSeccionList;
        this.asignacionHorarioList = asignacionHorarioList;
    }

    @ValueRangeProvider(id = "aulaRange")
    public List<Aula> getAulaList() {
        return aulaList;
    }

    public void setAulaList(List<Aula> aulaList) {
        this.aulaList = aulaList;
    }

    @ValueRangeProvider(id = "bloqueHorarioRange")
    public List<BloqueHorario> getBloqueHorarioList() {
        return bloqueHorarioList;
    }

    public void setBloqueHorarioList(List<BloqueHorario> bloqueHorarioList) {
        this.bloqueHorarioList = bloqueHorarioList;
    }

    public List<Docente> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<Docente> docenteList) {
        this.docenteList = docenteList;
    }

    public List<CursoSeccion> getCursoSeccionList() {
        return cursoSeccionList;
    }

    public void setCursoSeccionList(List<CursoSeccion> cursoSeccionList) {
        this.cursoSeccionList = cursoSeccionList;
    }

    public List<AsignacionHorario> getAsignacionHorarioList() {
        return asignacionHorarioList;
    }

    public void setAsignacionHorarioList(List<AsignacionHorario> asignacionHorarioList) {
        this.asignacionHorarioList = asignacionHorarioList;
    }

    public HardSoftScore getScore() {
        return score;
    }

    public void setScore(HardSoftScore score) {
        this.score = score;
    }
}
