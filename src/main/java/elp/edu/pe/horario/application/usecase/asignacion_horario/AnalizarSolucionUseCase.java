package elp.edu.pe.horario.application.usecase.asignacion_horario;


import elp.edu.pe.horario.domain.model.AsignacionHorario;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnalizarSolucionUseCase {

    public int obtenerCantidadAsignaciones(List<AsignacionHorario> asignaciones) {
        return asignaciones.size();
    }

    public Set<UUID> obtenerAulasUsadas(List<AsignacionHorario> asignaciones) {
        return asignaciones.stream()
                .map(a -> a.getAula().getId())
                .collect(Collectors.toSet());
    }

    public Set<String> obtenerBloquesUsados(List<AsignacionHorario> asignaciones) {
        return asignaciones.stream()
                .map(a -> a.getBloqueHorario().toString())
                .collect(Collectors.toSet());
    }

    public int obtenerCantidadDocentesAsignados(List<AsignacionHorario> asignaciones) {
        Map<String, List<AsignacionHorario>> asignacionesPorDocente = asignaciones.stream()
                .collect(Collectors.groupingBy(a -> String.valueOf(a.getDocente().getId())));

        return asignacionesPorDocente.size();
    }

}