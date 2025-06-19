package elp.edu.pe.horario.domain.solver;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import java.util.Comparator;

public class AsignacionHorarioDifficultyComparator implements Comparator<AsignacionHorario> {
    @Override
    public int compare(AsignacionHorario a1, AsignacionHorario a2) {
        // Validación de nulos
        if (a1 == null || a2 == null ||
                a1.getCursoSeccionDocente() == null || a2.getCursoSeccionDocente() == null ||
                a1.getCursoSeccionDocente().getDocente() == null || a2.getCursoSeccionDocente().getDocente() == null) {
            return 0;
        }

        // Prioriza docentes con más horas contratadas
        int horasComparison = Integer.compare(
                a2.getCursoSeccionDocente().getDocente().getHorasContratadas(),
                a1.getCursoSeccionDocente().getDocente().getHorasContratadas()
        );

        // Si tienen las mismas horas contratadas, ordena por ID
        return horasComparison != 0 ? horasComparison : a1.getId().compareTo(a2.getId());
    }
}