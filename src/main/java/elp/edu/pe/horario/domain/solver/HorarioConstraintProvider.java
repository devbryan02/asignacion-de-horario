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
                evitarCruceDisponibilidades(constraintFactory),
                evitarSuperposicionPorDia(constraintFactory),
                evitarMultiplesCursosPorSeccionPorDia(constraintFactory),
                distribuirCursosPorSemana(constraintFactory),
                limitarCargaDiariaPorSeccion(constraintFactory),
                evitarCrucesPeriodoAcademico(constraintFactory),
                distribuirCursosSeccionPorDia(constraintFactory),
                maximizarUsoAulas(constraintFactory),
                incentivarDistribucionBloques(constraintFactory),
                evitarConcentracionBloques(constraintFactory),
                distribuirBloquesPorDia(constraintFactory),

        };
    }

    private Constraint limitarCargaDiariaPorSeccion(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getSeccion() : null;
                        },
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.sum(a -> {
                            var bloque = a.getBloqueHorario();
                            if (bloque == null) return 0;
                            return Math.toIntExact(Duration.between(bloque.getHoraInicio(), bloque.getHoraFin()).toHours());
                        })
                )
                .filter((seccion, dia, horasTotales) -> horasTotales > 6) // máximo 6 horas por día
                .penalize(HardSoftScore.ONE_HARD.multiply(50000),
                        (seccion, dia, horasTotales) -> (int)(horasTotales - 6))
                .asConstraint("Limitar horas diarias por sección");
    }

    private Constraint distribuirCursosSeccionPorDia(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getSeccion() : null;
                        },
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.count()
                )
                .filter((seccion, dia, count) -> count > 3) // máximo 3 cursos por día
                .penalize(HardSoftScore.ONE_HARD.multiply(40000),
                        (seccion, dia, count) -> count - 3)
                .asConstraint("Distribuir cursos por día para cada sección");
    }

    private Constraint evitarCrucesPeriodoAcademico(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getSeccion().getPeriodo() : null;
                        }))
                .filter((a1, a2) -> {
                    var csd1 = a1.getCursoSeccionDocente();
                    var csd2 = a2.getCursoSeccionDocente();
                    if (csd1 == null || csd2 == null) return false;

                    // Verificar si es la misma sección en el mismo período
                    boolean mismaSeccion = csd1.getSeccion().equals(csd2.getSeccion());
                    if (!mismaSeccion) return false;

                    // Verificar superposición de horarios
                    var bloque1 = a1.getBloqueHorario();
                    var bloque2 = a2.getBloqueHorario();
                    if (bloque1 == null || bloque2 == null) return false;

                    return bloque1.getDiaSemana().equals(bloque2.getDiaSemana()) &&
                            bloquesSeSolapan(
                                    bloque1.getHoraInicio(),
                                    bloque1.getHoraFin(),
                                    bloque2.getHoraInicio(),
                                    bloque2.getHoraFin()
                            );
                })
                .penalize(HardSoftScore.ONE_HARD.multiply(100000))
                .asConstraint("Evitar cruces de horarios por período académico");
    }

    // Restricción para evitar que una sección tenga más de un curso en el mismo día
    private Constraint evitarMultiplesCursosPorSeccionPorDia(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getSeccion() : null;
                        }),
                        equal(a -> a.getBloqueHorario().getDiaSemana()))
                .filter((a1, a2) ->
                        a1.getCursoSeccionDocente() != null &&
                                a2.getCursoSeccionDocente() != null &&
                                !a1.getCursoSeccionDocente().getCurso().equals(a2.getCursoSeccionDocente().getCurso()))
                .penalize(HardSoftScore.ONE_HARD.multiply(10000))
                .asConstraint("Evitar múltiples cursos por sección en el mismo día");
    }

    // Restricción para distribuir las clases de un curso a lo largo de la semana
    private Constraint distribuirCursosPorSemana(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getCurso() : null;
                        },
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.count()
                )
                .filter((curso, dia, count) -> count > 1)
                .penalize(HardSoftScore.ONE_SOFT.multiply(30),
                        (curso, dia, count) -> count - 1)
                .asConstraint("Distribuir clases del mismo curso en diferentes días");
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
        // Primera parte: Recompensar el uso de bloques diferentes
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getBloqueHorario)
                .reward(HardSoftScore.ONE_SOFT.multiply(5000))
                .asConstraint("Incentivar uso de bloques distintos");
    }

    // Nueva restricción para penalizar la concentración en pocos bloques
    private Constraint evitarConcentracionBloques(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        AsignacionHorario::getBloqueHorario,
                        ConstraintCollectors.count()
                )
                .filter((bloque, count) -> count > 2)
                .penalize(HardSoftScore.ONE_HARD.multiply(20000),
                        (bloque, count) -> count - 2)
                .asConstraint("Evitar concentración en pocos bloques");
    }

    // Nueva restricción para distribuir bloques por día
    private Constraint distribuirBloquesPorDia(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.countDistinct(AsignacionHorario::getBloqueHorario)
                )
                .reward(HardSoftScore.ONE_SOFT.multiply(3000),
                        (dia, countBloques) -> countBloques)
                .asConstraint("Distribuir bloques por día");
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
                        equal(a -> a.getCursoSeccionDocente().getDocente()))
                .filter((a1, a2) ->
                        a1.getBloqueHorario().getDiaSemana().equals(a2.getBloqueHorario().getDiaSemana()) &&
                                bloquesSeSolapan(
                                        a1.getBloqueHorario().getHoraInicio(),
                                        a1.getBloqueHorario().getHoraFin(),
                                        a2.getBloqueHorario().getHoraInicio(),
                                        a2.getBloqueHorario().getHoraFin()
                                ))
                .penalize(HardSoftScore.ONE_HARD.multiply(100000))
                .asConstraint("Docente con horarios superpuestos");
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
                    // Si el docente no tiene restricciones, permitir cualquier bloque
                    if (docente == null || docente.getRestricciones() == null) return false;
                    var bloque = asignacion.getBloqueHorario();
                    if (bloque == null || bloque.getDiaSemana() == null ||
                            bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return false;

                    // Solo verificar restricciones si el docente las tiene definidas
                    return docente.getRestricciones().stream().anyMatch(restriccion ->
                            restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                                    restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                    restriccion.getHoraInicio() != null && restriccion.getHoraFin() != null &&
                                    bloquesSeSolapan(
                                            bloque.getHoraInicio(),
                                            bloque.getHoraFin(),
                                            restriccion.getHoraInicio(),
                                            restriccion.getHoraFin()
                                    )
                    );
                })
                .penalize(HardSoftScore.ONE_HARD.multiply(100))
                .asConstraint("Evitar bloques restringidos por docente");
    }

    private Constraint preferirBloquesDisponibles(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(asignacion -> {
                    if (asignacion == null) return false;
                    var csd = asignacion.getCursoSeccionDocente();
                    if (csd == null) return false;
                    var docente = csd.getDocente();
                    // Si el docente no tiene restricciones, no aplicar recompensa
                    if (docente == null || docente.getRestricciones() == null) return false;
                    var bloque = asignacion.getBloqueHorario();
                    if (bloque == null || bloque.getDiaSemana() == null ||
                            bloque.getHoraInicio() == null || bloque.getHoraFin() == null) return false;

                    // Solo aplicar recompensa si el docente tiene restricciones definidas
                    return docente.getRestricciones().stream().anyMatch(restriccion ->
                            restriccion.getTipoRestriccion() == TipoRestriccion.DISPONIBLE &&
                                    restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                                    restriccion.getHoraInicio() != null && restriccion.getHoraFin() != null &&
                                    bloquesSeSolapan(
                                            bloque.getHoraInicio(),
                                            bloque.getHoraFin(),
                                            restriccion.getHoraInicio(),
                                            restriccion.getHoraFin()
                                    )
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
                .penalize(HardSoftScore.of(100000, 0))
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
                .filter((aula, count) -> aula != null && count > 2)  // máximo 2 clases por aula por día
                .penalize(HardSoftScore.ONE_HARD.multiply(30000),    // aumentamos penalización
                        (aula, count) -> count - 2)
                .asConstraint("Distribuir clases entre aulas");
    }

    private Constraint maximizarUsoAulas(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getAula)
                .reward(HardSoftScore.ONE_SOFT.multiply(5000))
                .asConstraint("Maximizar uso de aulas diferentes");
    }

    /**
     * Metodo auxiliar para verificar solapamiento más robusto
     */
    private boolean bloquesSeSolapan(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }
}