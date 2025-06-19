package elp.edu.pe.horario.application.usecase.asignacion_horario;


import elp.edu.pe.horario.domain.model.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AnalizarSolucionUseCase {

    public int obtenerCantidadAsignaciones(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null) {
            return 0;
        }
        return asignaciones.size();
    }

    public Set<UUID> obtenerAulasUsadas(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null || asignaciones.isEmpty()) {
            return Collections.emptySet();
        }

        return asignaciones.stream()
                .filter(Objects::nonNull)
                .map(AsignacionHorario::getAula)
                .filter(Objects::nonNull)
                .map(Aula::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<String> obtenerBloquesUsados(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null || asignaciones.isEmpty()) {
            return Collections.emptySet();
        }

        return asignaciones.stream()
                .filter(Objects::nonNull)
                .map(AsignacionHorario::getBloqueHorario)
                .filter(Objects::nonNull)
                .map(BloqueHorario::toString)
                .collect(Collectors.toSet());
    }

    public int obtenerCantidadDocentesAsignados(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null || asignaciones.isEmpty()) {
            return 0;
        }

        return (int) asignaciones.stream()
                .filter(Objects::nonNull)
                .map(AsignacionHorario::getCursoSeccionDocente)
                .filter(Objects::nonNull)
                .map(CursoSeccionDocente::getDocente)
                .filter(Objects::nonNull)
                .map(Docente::getId)
                .filter(Objects::nonNull)
                .distinct()
                .count();
    }

}