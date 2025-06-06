package elp.edu.pe.horario.domain.solver;

import elp.edu.pe.horario.domain.enums.TipoRestriccion;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.model.Docente;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.time.Duration;

import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class HorarioConstraintProvider implements ConstraintProvider {

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                aulaNoSuperpuesta(constraintFactory),
                docenteNoSuperpuesto(constraintFactory),
                evitarRestriccionesDocente(constraintFactory),
                limitarHorasContratadasPorDocente(constraintFactory),
                balancearHorasDocente(constraintFactory),
                preferirBloquesDisponibles(constraintFactory),
                limitarHorasPorDiaPorDocente(constraintFactory)
        };
    }

    // Regla 1: Un aula no puede estar ocupada en dos asignaciones al mismo tiempo
    private Constraint aulaNoSuperpuesta(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .join(AsignacionHorario.class,
                        equal(AsignacionHorario::getAula),
                        equal(AsignacionHorario::getBloqueHorario))
                .filter((a1, a2) -> !a1.equals(a2))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Aula ocupada en mismo bloque");
    }

    // Regla 2: Un docente no puede estar en dos lugares al mismo tiempo
    private Constraint docenteNoSuperpuesto(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .join(AsignacionHorario.class,
                        equal(AsignacionHorario::getDocente),
                        equal(AsignacionHorario::getBloqueHorario))
                .filter((a1, a2) -> !a1.equals(a2))
                .penalize(HardSoftScore.ONE_HARD)
                .asConstraint("Docente duplicado en mismo bloque");
    }

    //  Regla 3: Evitar bloques que el docente ha restringido
    private Constraint evitarRestriccionesDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(asignacion -> {
                    Docente docente = asignacion.getDocente();
                    BloqueHorario bloque = asignacion.getBloqueHorario();

                    return docente != null &&
                            docente.getRestricciones() != null &&
                            bloque != null &&
                            docente.getRestricciones().stream().anyMatch(restriccion ->
                                    restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                                            restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                            bloque.getHoraInicio().isBefore(restriccion.getHoraFin()) &&
                                            bloque.getHoraFin().isAfter(restriccion.getHoraInicio())
                            );
                })
                .penalize(HardSoftScore.ONE_SOFT, asignacion -> {
                    BloqueHorario bloque = asignacion.getBloqueHorario();

                    return asignacion.getDocente().getRestricciones().stream()
                            .filter(restriccion ->
                                    restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                                            restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                            bloque.getHoraInicio().isBefore(restriccion.getHoraFin()) &&
                                            bloque.getHoraFin().isAfter(restriccion.getHoraInicio())
                            )
                            .mapToInt(restriccion -> {
                                var inicio = bloque.getHoraInicio().isAfter(restriccion.getHoraInicio())
                                        ? bloque.getHoraInicio()
                                        : restriccion.getHoraInicio();
                                var fin = bloque.getHoraFin().isBefore(restriccion.getHoraFin())
                                        ? bloque.getHoraFin()
                                        : restriccion.getHoraFin();
                                return (int) Duration.between(inicio, fin).toMinutes();
                            })
                            .sum();
                })
                .asConstraint("Evitar bloques restringidos por docente (suavizado)");
    }


    private Constraint preferirBloquesDisponibles(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(asignacion -> {
                    Docente docente = asignacion.getDocente();
                    if (docente == null || docente.getRestricciones() == null) return false;

                    BloqueHorario bloque = asignacion.getBloqueHorario();
                    if (bloque == null) return false;

                    // Recompensar si el docente marcó este bloque como DISPONIBLE
                    return docente.getRestricciones().stream().anyMatch(restriccion ->
                            restriccion.getTipoRestriccion() == TipoRestriccion.DISPONIBLE &&
                                    restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                    bloque.getHoraInicio().isBefore(restriccion.getHoraFin()) &&
                                    bloque.getHoraFin().isAfter(restriccion.getHoraInicio())
                    );
                })
                .reward(HardSoftScore.ONE_SOFT)
                .asConstraint("Preferir bloques disponibles por el docente");
    }



    /**
     * Restricción que verifica que los docentes no excedan sus horas contratadas
     */
    public Constraint limitarHorasContratadasPorDocente(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        AsignacionHorario::getDocente,
                        ConstraintCollectors.sum(ah -> {
                            Duration duracion = Duration.between(ah.getBloqueHorario().getHoraInicio(),
                                    ah.getBloqueHorario().getHoraFin());
                            return (int) duracion.toHours();
                        })
                )
                .filter((docente, totalHoras) -> totalHoras > docente.getHorasContratadas())
                .penalize(HardSoftScore.ONE_HARD,
                        (docente, totalHoras) -> totalHoras - docente.getHorasContratadas())
                .asConstraint("Excede horas contratadas");
    }

    /**
     * Restricción que recompensa cuando las horas asignadas se aproximan a las horas contratadas
     * Busca optimizar el uso del tiempo contratado de los docentes
     */
    public Constraint balancearHorasDocente(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getDocente, 
                    ConstraintCollectors.sum(ah -> {
                        Duration duracion = Duration.between(ah.getBloqueHorario().getHoraInicio(), 
                                ah.getBloqueHorario().getHoraFin());
                        return (int) duracion.toHours();
                    }))
            .reward(HardSoftScore.ONE_SOFT,
                    (docente, totalHoras) -> {
                        int diferencia = docente.getHorasContratadas() - totalHoras;
                        return Math.max(0, docente.getHorasContratadas() - diferencia);
                    })
            .asConstraint("Aproximarse a horas contratadas");
    }

    /**
     * Restricción que verifica que los docentes no excedan sus horas máximas por día
     */
    public Constraint limitarHorasPorDiaPorDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        AsignacionHorario::getDocente,
                        asignacion -> asignacion.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.sum(asignacion -> {
                            Duration duracion = Duration.between(
                                    asignacion.getBloqueHorario().getHoraInicio(),
                                    asignacion.getBloqueHorario().getHoraFin());
                            return (int) duracion.toHours();
                        })
                )
                .filter((docente, dia, totalHoras) ->
                        docente != null && docente.getHorasMaximasPorDia() != null &&
                                totalHoras > docente.getHorasMaximasPorDia())
                .penalize(HardSoftScore.ONE_HARD,
                        (docente, dia, totalHoras) ->
                                totalHoras - docente.getHorasMaximasPorDia())
                .asConstraint("Excede horas máximas por día para docente");
    }
}