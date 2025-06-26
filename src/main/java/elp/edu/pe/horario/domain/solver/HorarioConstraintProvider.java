package elp.edu.pe.horario.domain.solver;

import elp.edu.pe.horario.domain.enums.TipoRestriccion;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

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
                limitarHorasPorDiaPorDocente(constraintFactory),
                seccionNoSuperpuesta(constraintFactory),
                distribuirHorariosPorDia(constraintFactory),
                evitarHuecos(constraintFactory),
                distribuirAulas(constraintFactory),
                incentivarDistribucionBloques(constraintFactory),
                evitarCruceDisponibilidades(constraintFactory),
                evitarSuperposicionPorDia(constraintFactory)
        };
    }

    private Constraint evitarSuperposicionPorDia(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        }))
                .filter((a1, a2) -> {
                    if (a1.getBloqueHorario() == null || a2.getBloqueHorario() == null) return false;
                    return a1.getBloqueHorario().getDiaSemana() == a2.getBloqueHorario().getDiaSemana() &&
                            bloquesSeSolapan(
                                    a1.getBloqueHorario().getHoraInicio(),
                                    a1.getBloqueHorario().getHoraFin(),
                                    a2.getBloqueHorario().getHoraInicio(),
                                    a2.getBloqueHorario().getHoraFin()
                            );
                })
                .penalize(HardSoftScore.ONE_HARD.multiply(10000))
                .asConstraint("Evitar superposición de horarios por día");
    }

    private Constraint evitarCruceDisponibilidades(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(asignacion -> {
                    if (asignacion == null) return false;
                    var csd = asignacion.getCursoSeccionDocente();
                    if (csd == null) return false;
                    var docente = csd.getDocente();
                    if (docente == null || docente.getRestricciones() == null) return false;
                    var bloque = asignacion.getBloqueHorario();
                    if (bloque == null || bloque.getDiaSemana() == null ||
                            bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return false;

                    // Verificar si hay restricciones que se solapan
                    var restricciones = docente.getRestricciones().stream()
                            .filter(r -> r.getDiaSemana() == bloque.getDiaSemana())
                            .toList();

                    // Contar solapamientos
                    long solapamientos = restricciones.stream()
                            .filter(r -> bloquesSeSolapan(
                                    bloque.getHoraInicio(),
                                    bloque.getHoraFin(),
                                    r.getHoraInicio(),
                                    r.getHoraFin()))
                            .count();

                    return solapamientos > 1; // Si hay más de un solapamiento, hay conflicto
                })
                .penalize(HardSoftScore.ONE_HARD.multiply(1000))
                .asConstraint("Evitar cruces de disponibilidades");
    }

    private Constraint incentivarDistribucionBloques(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getBloqueHorario)
                .reward(HardSoftScore.ONE_SOFT.multiply(15))
                .asConstraint("Incentivar uso de más bloques distintos");
    }


    // Regla 1: Un aula no puede estar ocupada en dos asignaciones al mismo tiempo
    private Constraint aulaNoSuperpuesta(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(AsignacionHorario::getAula))
                .filter((a1, a2) ->
                        a1.getAula() != null && a2.getAula() != null &&
                                a1.getBloqueHorario() != null && a2.getBloqueHorario() != null &&
                                a1.getBloqueHorario().getDiaSemana() != null && a2.getBloqueHorario().getDiaSemana() != null &&
                                a1.getBloqueHorario().getDiaSemana().equals(a2.getBloqueHorario().getDiaSemana()) &&
                                bloquesSeSolapan(
                                        a1.getBloqueHorario().getHoraInicio(),
                                        a1.getBloqueHorario().getHoraFin(),
                                        a2.getBloqueHorario().getHoraInicio(),
                                        a2.getBloqueHorario().getHoraFin()
                                ))
                .penalize(HardSoftScore.of(100000, 0))
                .asConstraint("Aula ocupada en mismo bloque");
    }

    // Regla 2: Un docente no puede estar en dos lugares al mismo tiempo
    private Constraint docenteNoSuperpuesto(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            if (a.getCursoSeccionDocente() == null) return null;
                            return a.getCursoSeccionDocente().getDocente();
                        }))
                .filter((a1, a2) ->
                        a1.getCursoSeccionDocente() != null &&
                                a2.getCursoSeccionDocente() != null &&
                                a1.getCursoSeccionDocente().getDocente() != null &&
                                a2.getCursoSeccionDocente().getDocente() != null &&
                                a1.getBloqueHorario() != null &&
                                a2.getBloqueHorario() != null &&
                                a1.getBloqueHorario().getDiaSemana() != null &&
                                a2.getBloqueHorario().getDiaSemana() != null &&
                                a1.getBloqueHorario().getDiaSemana().equals(a2.getBloqueHorario().getDiaSemana()) &&
                                bloquesSeSolapan(
                                        a1.getBloqueHorario().getHoraInicio(),
                                        a1.getBloqueHorario().getHoraFin(),
                                        a2.getBloqueHorario().getHoraInicio(),
                                        a2.getBloqueHorario().getHoraFin()
                                ))
                .penalize(HardSoftScore.of(100000, 0))
                .asConstraint("Docente duplicado en mismo bloque");
    }

    // Regla 3: Evitar bloques que el docente ha restringido
    private Constraint evitarRestriccionesDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(asignacion -> {
                    if (asignacion == null) return false;
                    var csd = asignacion.getCursoSeccionDocente();
                    if (csd == null) return false;
                    var docente = csd.getDocente();
                    if (docente == null || docente.getRestricciones() == null) return false;
                    var bloque = asignacion.getBloqueHorario();
                    if (bloque == null || bloque.getDiaSemana() == null ||
                            bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return false;

                    // Buscar si hay alguna restricción BLOQUEADO que solape con el bloque
                    return docente.getRestricciones().stream().anyMatch(restriccion ->
                            restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                                    restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                    restriccion.getHoraInicio() != null && restriccion.getHoraFin() != null &&
                                    bloque.getHoraInicio().isBefore(restriccion.getHoraFin()) &&
                                    bloque.getHoraFin().isAfter(restriccion.getHoraInicio())
                    );
                })
                .penalize(HardSoftScore.ONE_HARD.multiply(100), asignacion -> {
                    var csd = asignacion.getCursoSeccionDocente();
                    var docente = csd != null ? csd.getDocente() : null;
                    var bloque = asignacion.getBloqueHorario();

                    if (docente == null || docente.getRestricciones() == null || bloque == null
                            || bloque.getDiaSemana() == null
                            || bloque.getHoraInicio() == null || bloque.getHoraFin() == null) {
                        return 0;
                    }

                    return docente.getRestricciones().stream()
                            .filter(restriccion ->
                                    restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                                            restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                            restriccion.getHoraInicio() != null && restriccion.getHoraFin() != null &&
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
                    if (asignacion == null) return false;
                    var csd = asignacion.getCursoSeccionDocente();
                    if (csd == null) return false;
                    var docente = csd.getDocente();
                    if (docente == null || docente.getRestricciones() == null) return false;
                    var bloque = asignacion.getBloqueHorario();
                    if (bloque == null || bloque.getDiaSemana() == null ||
                            bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return false;

                    // Recompensar si el docente marcó este bloque como DISPONIBLE
                    return docente.getRestricciones().stream().anyMatch(restriccion ->
                            restriccion.getTipoRestriccion() == TipoRestriccion.DISPONIBLE &&
                                    restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                    restriccion.getHoraInicio() != null && restriccion.getHoraFin() != null &&
                                    bloque.getHoraInicio().isBefore(restriccion.getHoraFin()) &&
                                    bloque.getHoraFin().isAfter(restriccion.getHoraInicio())
                    );
                })
                .reward(HardSoftScore.ONE_SOFT.multiply(40))
                .asConstraint("Preferir bloques disponibles por el docente");
    }

    /**
     * Restricción que verifica que los docentes no excedan sus horas contratadas
     */
    public Constraint limitarHorasContratadasPorDocente(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        },
                        ConstraintCollectors.sum(ah -> {
                            var bloque = ah.getBloqueHorario();
                            if (bloque == null || bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return 0;
                            Duration duracion = Duration.between(bloque.getHoraInicio(), bloque.getHoraFin());
                            return (int) duracion.toHours();
                        })
                )
                .filter((docente, totalHoras) ->
                        docente != null && docente.getHorasContratadas() != null && totalHoras > docente.getHorasContratadas()
                )
                .penalize(HardSoftScore.ONE_HARD.multiply(5000),
                        (docente, totalHoras) -> docente.getHorasContratadas() != null
                                ? totalHoras - docente.getHorasContratadas()
                                : 0
                )
                .asConstraint("Excede horas contratadas");
    }

    /**
     * Restricción que recompensa cuando las horas asignadas se aproximan a las horas contratadas
     */
    public Constraint balancearHorasDocente(ConstraintFactory constraintFactory) {
        return constraintFactory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        },
                        ConstraintCollectors.sum(ah -> {
                            var bloque = ah.getBloqueHorario();
                            if (bloque == null || bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return 0;
                            Duration duracion = Duration.between(bloque.getHoraInicio(), bloque.getHoraFin());
                            return (int) duracion.toHours();
                        })
                )
                .reward(HardSoftScore.ONE_SOFT.multiply(25),
                        (docente, totalHoras) -> {
                            if (docente == null || docente.getHorasContratadas() == null) return 0;
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
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        },
                        asignacion -> {
                            var bloque = asignacion.getBloqueHorario();
                            return (bloque != null) ? bloque.getDiaSemana() : null;
                        },
                        ConstraintCollectors.sum(asignacion -> {
                            var bloque = asignacion.getBloqueHorario();
                            if (bloque == null || bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return 0;
                            Duration duracion = Duration.between(bloque.getHoraInicio(), bloque.getHoraFin());
                            return (int) duracion.toHours();
                        })
                )
                .filter((docente, dia, totalHoras) ->
                        docente != null &&
                                docente.getHorasMaximasPorDia() != null &&
                                dia != null &&
                                totalHoras > docente.getHorasMaximasPorDia()
                )
                .penalize(HardSoftScore.ONE_HARD.multiply(5000),
                        (docente, dia, totalHoras) ->
                                (docente != null && docente.getHorasMaximasPorDia() != null)
                                        ? totalHoras - docente.getHorasMaximasPorDia()
                                        : 0
                )
                .asConstraint("Excede horas máximas por día para docente");
    }
    /**
     * Restricción que verifica que no haya superposición de bloques para la misma sección
     */
    private Constraint seccionNoSuperpuesta(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getSeccion() : null;
                        }))
                .filter((a1, a2) ->
                        a1.getCursoSeccionDocente() != null &&
                                a2.getCursoSeccionDocente() != null &&
                                a1.getCursoSeccionDocente().getSeccion() != null &&
                                a2.getCursoSeccionDocente().getSeccion() != null &&
                                a1.getBloqueHorario() != null &&
                                a2.getBloqueHorario() != null &&
                                a1.getBloqueHorario().getDiaSemana() != null &&
                                a2.getBloqueHorario().getDiaSemana() != null &&
                                a1.getBloqueHorario().getHoraInicio() != null &&
                                a1.getBloqueHorario().getHoraFin() != null &&
                                a2.getBloqueHorario().getHoraInicio() != null &&
                                a2.getBloqueHorario().getHoraFin() != null &&
                                a1.getBloqueHorario().getDiaSemana().equals(a2.getBloqueHorario().getDiaSemana()) &&
                                bloquesSeSolapan(
                                        a1.getBloqueHorario().getHoraInicio(),
                                        a1.getBloqueHorario().getHoraFin(),
                                        a2.getBloqueHorario().getHoraInicio(),
                                        a2.getBloqueHorario().getHoraFin()
                                )
                )
                .penalize(HardSoftScore.of(10000, 0))
                .asConstraint("Sección con horarios superpuestos");
    }

    // En HorarioConstraintProvider
    private Constraint distribuirHorariosPorDia(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        },
                        a -> {
                            var bloque = a.getBloqueHorario();
                            return bloque != null ? bloque.getDiaSemana() : null;
                        },
                        ConstraintCollectors.count()
                )
                .filter((docente, dia, count) ->
                        docente != null && dia != null && count > 3
                )
                .penalize(HardSoftScore.ONE_SOFT.multiply(50),
                        (docente, dia, count) -> count - 3)
                .asConstraint("No más de 3 clases por día por docente");
    }

    private Constraint evitarHuecos(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getDocente() : null;
                        },
                        a -> {
                            var bloque = a.getBloqueHorario();
                            return bloque != null ? bloque.getDiaSemana() : null;
                        },
                        ConstraintCollectors.toList()
                )
                .penalize(HardSoftScore.ONE_SOFT.multiply(30),
                        (docente, dia, asignaciones) -> {
                            if (docente == null || dia == null || asignaciones == null) return 0;
                            return calcularHuecos(asignaciones);
                        })
                .asConstraint("Minimizar huecos entre clases");
    }

    private int calcularHuecos(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null || asignaciones.size() <= 1) return 0;

        // Filtrar asignaciones con bloque o horas inválidas
        List<AsignacionHorario> filtradas = new java.util.ArrayList<>(asignaciones.stream()
                .filter(a -> a != null &&
                        a.getBloqueHorario() != null &&
                        a.getBloqueHorario().getHoraInicio() != null &&
                        a.getBloqueHorario().getHoraFin() != null)
                .toList());

        if (filtradas.size() <= 1) return 0;

        filtradas.sort(Comparator.comparing(a -> a.getBloqueHorario().getHoraInicio()));

        int huecos = 0;
        for (int i = 0; i < filtradas.size() - 1; i++) {
            var finActual = filtradas.get(i).getBloqueHorario().getHoraFin();
            var inicioSiguiente = filtradas.get(i + 1).getBloqueHorario().getHoraInicio();

            if (finActual != null && inicioSiguiente != null) {
                Duration gap = Duration.between(finActual, inicioSiguiente);
                if (gap.toHours() > 2) {
                    huecos += (int) (gap.toHours() - 2);
                }
            }
        }
        return huecos;
    }

    private Constraint distribuirAulas(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        asignacion -> asignacion != null ? asignacion.getAula() : null,
                        ConstraintCollectors.count()
                )
                .filter((aula, count) -> aula != null && count > 3)  // más de 3 clases por aula
                .penalize(HardSoftScore.ONE_SOFT.multiply(20),
                        (aula, count) -> count - 3)
                .asConstraint("Distribuir clases entre aulas");
    }

    /**
     * Metodo auxiliar para verificar solapamiento más robusto
     */
    private boolean bloquesSeSolapan(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }
}