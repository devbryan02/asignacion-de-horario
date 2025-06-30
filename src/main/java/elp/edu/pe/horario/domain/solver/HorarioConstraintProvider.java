package elp.edu.pe.horario.domain.solver;

import elp.edu.pe.horario.domain.enums.TipoRestriccion;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.Seccion;
import elp.edu.pe.horario.domain.model.BloqueHorario;
import elp.edu.pe.horario.domain.model.CursoSeccionDocente;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.score.stream.Constraint;
import org.optaplanner.core.api.score.stream.ConstraintCollectors;
import org.optaplanner.core.api.score.stream.ConstraintFactory;
import org.optaplanner.core.api.score.stream.ConstraintProvider;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import static org.optaplanner.core.api.score.stream.Joiners.equal;

public class HorarioConstraintProvider implements ConstraintProvider {

    // Constantes para scores
    private static final int HARD_SCORE_HIGH = 100000;
    private static final int HARD_SCORE_MEDIUM = 50000;
    private static final int HARD_SCORE_LOW = 10000;
    private static final int SOFT_SCORE_HIGH = 5000;
    private static final int SOFT_SCORE_MEDIUM = 3000;
    private static final int SOFT_SCORE_LOW = 1000;

    // Constantes para límites
    private static final int MAX_HORAS_POR_DIA_SECCION = 6;
    private static final int MAX_CURSOS_POR_DIA_SECCION = 3;
    private static final int MAX_CLASES_POR_DIA_DOCENTE = 3;
    private static final int MAX_CLASES_POR_AULA = 2;
    private static final int MAX_HUECOS_PERMITIDOS = 2;

    @Override
    public Constraint[] defineConstraints(ConstraintFactory constraintFactory) {
        return new Constraint[]{
                // Restricciones HARD - No pueden violarse
                aulaNoSuperpuesta(constraintFactory),
                docenteNoSuperpuesto(constraintFactory),
                seccionNoSuperpuesta(constraintFactory),
                evitarRestriccionesDocente(constraintFactory),
                limitarHorasContratadasPorDocente(constraintFactory),
                limitarHorasPorDiaPorDocente(constraintFactory),
                limitarCargaDiariaPorSeccion(constraintFactory),
                distribuirCursosSeccionPorDia(constraintFactory),
                evitarCrucesPeriodoAcademico(constraintFactory),
                evitarMultiplesCursosPorSeccionPorDia(constraintFactory),
                evitarSuperposicionPorDia(constraintFactory),
                evitarCruceDisponibilidades(constraintFactory),
                evitarConcentracionBloques(constraintFactory),
                distribuirAulas(constraintFactory),

                // Restricciones SOFT - Preferencias
                balancearHorasDocente(constraintFactory),
                preferirBloquesDisponibles(constraintFactory),
                distribuirHorariosPorDia(constraintFactory),
                evitarHuecos(constraintFactory),
                distribuirCursosPorSemana(constraintFactory),
                incentivarDistribucionBloques(constraintFactory),
                distribuirBloquesPorDia(constraintFactory),
                maximizarUsoAulas(constraintFactory)
        };
    }

    // ================= MÉTODOS AUXILIARES =================

    /**
     * Extrae el docente de una asignación de forma segura
     */
    private Docente extraerDocente(AsignacionHorario asignacion) {
        if (asignacion == null) return null;
        CursoSeccionDocente csd = asignacion.getCursoSeccionDocente();
        return csd != null ? csd.getDocente() : null;
    }

    /**
     * Extrae la sección de una asignación de forma segura
     */
    private Seccion extraerSeccion(AsignacionHorario asignacion) {
        if (asignacion == null) return null;
        CursoSeccionDocente csd = asignacion.getCursoSeccionDocente();
        return csd != null ? csd.getSeccion() : null;
    }

    /**
     * Calcula las horas de duración de un bloque horario
     */
    private int calcularHorasBloque(BloqueHorario bloque) {
        if (bloque == null || bloque.getHoraInicio() == null || bloque.getHoraFin() == null) {
            return 0;
        }
        return Math.toIntExact(Duration.between(bloque.getHoraInicio(), bloque.getHoraFin()).toHours());
    }

    /**
     * Verifica si dos bloques horarios se solapan en el mismo día
     */
    private boolean bloquesSeSolapanMismoDia(AsignacionHorario a1, AsignacionHorario a2) {
        BloqueHorario b1 = a1.getBloqueHorario();
        BloqueHorario b2 = a2.getBloqueHorario();

        if (b1 == null || b2 == null) return false;
        if (!b1.getDiaSemana().equals(b2.getDiaSemana())) return false;

        return bloquesSeSolapan(b1.getHoraInicio(), b1.getHoraFin(),
                b2.getHoraInicio(), b2.getHoraFin());
    }

    /**
     * Verifica si hay solapamiento entre horarios
     */
    private boolean bloquesSeSolapan(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        if (inicio1 == null || fin1 == null || inicio2 == null || fin2 == null) {
            return false;
        }
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }

    /**
     * Verifica si una asignación es válida (no nula y con datos completos)
     */
    private boolean esAsignacionValida(AsignacionHorario asignacion) {
        return asignacion != null &&
                asignacion.getBloqueHorario() != null &&
                asignacion.getCursoSeccionDocente() != null;
    }

    /**
     * Crea restricción de no superposición genérica
     */
    private Constraint crearRestriccionNoSuperposicion(
            ConstraintFactory factory,
            Function<AsignacionHorario, Object> keyExtractor,
            String constraintName,
            int penalizacion) {

        return factory
                .forEachUniquePair(AsignacionHorario.class, equal(keyExtractor))
                .filter(this::bloquesSeSolapanMismoDia)
                .penalize(HardSoftScore.ONE_HARD.multiply(penalizacion))
                .asConstraint(constraintName);
    }

    /**
     * Crea restricción de límite por agrupación
     */
    private <T, U> Constraint crearRestriccionLimite(
            ConstraintFactory factory,
            Function<AsignacionHorario, T> keyExtractor1,
            Function<AsignacionHorario, U> keyExtractor2,
            ToIntFunction<AsignacionHorario> valueCalculator,
            int limite,
            String constraintName,
            int penalizacion,
            boolean esHard) {

        if (esHard) {
            return factory
                    .forEach(AsignacionHorario.class)
                    .groupBy(keyExtractor1, keyExtractor2,
                            ConstraintCollectors.sum(valueCalculator))
                    .filter((key1, key2, total) -> total > limite)
                    .penalize(HardSoftScore.ofHard(penalizacion),
                            (key1, key2, total) -> total - limite)
                    .asConstraint(constraintName);
        } else {
            return factory
                    .forEach(AsignacionHorario.class)
                    .groupBy(keyExtractor1, keyExtractor2,
                            ConstraintCollectors.sum(valueCalculator))
                    .filter((key1, key2, total) -> total > limite)
                    .penalize(HardSoftScore.ofSoft(penalizacion),
                            (key1, key2, total) -> total - limite)
                    .asConstraint(constraintName);
        }
    }

    // ================= RESTRICCIONES HARD =================

    private Constraint aulaNoSuperpuesta(ConstraintFactory factory) {
        return crearRestriccionNoSuperposicion(
                factory,
                AsignacionHorario::getAula,
                "Aula ocupada en mismo bloque",
                HARD_SCORE_HIGH
        );
    }

    private Constraint docenteNoSuperpuesto(ConstraintFactory factory) {
        return crearRestriccionNoSuperposicion(
                factory,
                this::extraerDocente,
                "Docente con horarios superpuestos",
                HARD_SCORE_HIGH
        );
    }

    private Constraint seccionNoSuperpuesta(ConstraintFactory factory) {
        return crearRestriccionNoSuperposicion(
                factory,
                this::extraerSeccion,
                "Sección con horarios superpuestos",
                HARD_SCORE_HIGH
        );
    }

    private Constraint evitarSuperposicionPorDia(ConstraintFactory factory) {
        return crearRestriccionNoSuperposicion(
                factory,
                this::extraerDocente,
                "Evitar superposición de horarios por día",
                HARD_SCORE_LOW
        );
    }

    private Constraint limitarCargaDiariaPorSeccion(ConstraintFactory factory) {
        return crearRestriccionLimite(
                factory,
                this::extraerSeccion,
                a -> a.getBloqueHorario().getDiaSemana(),
                a -> calcularHorasBloque(a.getBloqueHorario()),
                MAX_HORAS_POR_DIA_SECCION,
                "Limitar horas diarias por sección",
                HARD_SCORE_MEDIUM,
                true
        );
    }

    private Constraint distribuirCursosSeccionPorDia(ConstraintFactory factory) {
        return crearRestriccionLimite(
                factory,
                this::extraerSeccion,
                a -> a.getBloqueHorario().getDiaSemana(),
                a -> 1, // Contar cada asignación como 1
                MAX_CURSOS_POR_DIA_SECCION,
                "Distribuir cursos por día para cada sección",
                40000,
                true
        );
    }

    private Constraint limitarHorasContratadasPorDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(this::extraerDocente,
                        ConstraintCollectors.sum(a -> calcularHorasBloque(a.getBloqueHorario())))
                .filter((docente, totalHoras) ->
                        docente != null && docente.getHorasContratadas() != null &&
                                totalHoras > docente.getHorasContratadas())
                .penalize(HardSoftScore.ONE_HARD.multiply(5000),
                        (docente, totalHoras) -> totalHoras - docente.getHorasContratadas())
                .asConstraint("Excede horas contratadas");
    }

    private Constraint limitarHorasPorDiaPorDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(this::extraerDocente,
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.sum(a -> calcularHorasBloque(a.getBloqueHorario())))
                .filter((docente, dia, totalHoras) ->
                        docente != null && docente.getHorasMaximasPorDia() != null &&
                                dia != null && totalHoras > docente.getHorasMaximasPorDia())
                .penalize(HardSoftScore.ONE_HARD.multiply(5000),
                        (docente, dia, totalHoras) -> totalHoras - docente.getHorasMaximasPorDia())
                .asConstraint("Excede horas máximas por día para docente");
    }

    private Constraint evitarRestriccionesDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(this::tieneRestriccionBloqueada)
                .penalize(HardSoftScore.ONE_HARD.multiply(100))
                .asConstraint("Evitar bloques restringidos por docente");
    }

    private boolean tieneRestriccionBloqueada(AsignacionHorario asignacion) {
        if (!esAsignacionValida(asignacion)) return false;

        Docente docente = extraerDocente(asignacion);
        if (docente == null || docente.getRestricciones() == null) return false;

        BloqueHorario bloque = asignacion.getBloqueHorario();

        return docente.getRestricciones().stream().anyMatch(restriccion ->
                restriccion.getTipoRestriccion() == TipoRestriccion.BLOQUEADO &&
                        restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                        bloquesSeSolapan(bloque.getHoraInicio(), bloque.getHoraFin(),
                                restriccion.getHoraInicio(), restriccion.getHoraFin()));
    }

    private Constraint evitarCrucesPeriodoAcademico(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(a -> {
                            Seccion seccion = extraerSeccion(a);
                            return seccion != null ? seccion.getPeriodo() : null;
                        }))
                .filter(this::sonMismaSeccionConSolapamiento)
                .penalize(HardSoftScore.ONE_HARD.multiply(HARD_SCORE_HIGH))
                .asConstraint("Evitar cruces de horarios por período académico");
    }

    private boolean sonMismaSeccionConSolapamiento(AsignacionHorario a1, AsignacionHorario a2) {
        Seccion s1 = extraerSeccion(a1);
        Seccion s2 = extraerSeccion(a2);
        return s1 != null && s1.equals(s2) && bloquesSeSolapanMismoDia(a1, a2);
    }

    private Constraint evitarMultiplesCursosPorSeccionPorDia(ConstraintFactory factory) {
        return factory
                .forEachUniquePair(AsignacionHorario.class,
                        equal(this::extraerSeccion),
                        equal(a -> a.getBloqueHorario().getDiaSemana()))
                .filter(this::sonCursosDiferentes)
                .penalize(HardSoftScore.ONE_HARD.multiply(HARD_SCORE_LOW))
                .asConstraint("Evitar múltiples cursos por sección en el mismo día");
    }

    private boolean sonCursosDiferentes(AsignacionHorario a1, AsignacionHorario a2) {
        var csd1 = a1.getCursoSeccionDocente();
        var csd2 = a2.getCursoSeccionDocente();
        return csd1 != null && csd2 != null &&
                !csd1.getCurso().equals(csd2.getCurso());
    }

    private Constraint evitarCruceDisponibilidades(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(this::tieneCruceDisponibilidades)
                .penalize(HardSoftScore.ONE_HARD.multiply(SOFT_SCORE_LOW))
                .asConstraint("Evitar cruces de disponibilidades");
    }

    private boolean tieneCruceDisponibilidades(AsignacionHorario asignacion) {
        if (!esAsignacionValida(asignacion)) return false;

        Docente docente = extraerDocente(asignacion);
        if (docente == null || docente.getRestricciones() == null) return false;

        BloqueHorario bloque = asignacion.getBloqueHorario();

        long solapamientos = docente.getRestricciones().stream()
                .filter(r -> r.getDiaSemana() == bloque.getDiaSemana())
                .filter(r -> bloquesSeSolapan(bloque.getHoraInicio(), bloque.getHoraFin(),
                        r.getHoraInicio(), r.getHoraFin()))
                .count();

        return solapamientos > 1;
    }

    private Constraint evitarConcentracionBloques(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getBloqueHorario, ConstraintCollectors.count())
                .filter((bloque, count) -> count > 2)
                .penalize(HardSoftScore.ONE_HARD.multiply(20000), (bloque, count) -> count - 2)
                .asConstraint("Evitar concentración en pocos bloques");
    }

    private Constraint distribuirAulas(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getAula, ConstraintCollectors.count())
                .filter((aula, count) -> aula != null && count > MAX_CLASES_POR_AULA)
                .penalize(HardSoftScore.ONE_HARD.multiply(30000), (aula, count) -> count - MAX_CLASES_POR_AULA)
                .asConstraint("Distribuir clases entre aulas");
    }

    // ================= RESTRICCIONES SOFT =================

    private Constraint balancearHorasDocente(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(this::extraerDocente,
                        ConstraintCollectors.sum(a -> calcularHorasBloque(a.getBloqueHorario())))
                .reward(HardSoftScore.ONE_SOFT.multiply(25), this::calcularRecompensaBalance)
                .asConstraint("Aproximarse a horas contratadas");
    }

    private int calcularRecompensaBalance(Docente docente, Integer totalHoras) {
        if (docente == null || docente.getHorasContratadas() == null) return 0;
        int diferencia = docente.getHorasContratadas() - totalHoras;
        return Math.max(0, docente.getHorasContratadas() - diferencia);
    }

    private Constraint preferirBloquesDisponibles(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .filter(this::esBloqueDisponible)
                .reward(HardSoftScore.ONE_SOFT.multiply(40))
                .asConstraint("Preferir bloques disponibles por el docente");
    }

    private boolean esBloqueDisponible(AsignacionHorario asignacion) {
        if (!esAsignacionValida(asignacion)) return false;

        Docente docente = extraerDocente(asignacion);
        if (docente == null || docente.getRestricciones() == null) return false;

        BloqueHorario bloque = asignacion.getBloqueHorario();

        return docente.getRestricciones().stream().anyMatch(restriccion ->
                restriccion.getTipoRestriccion() == TipoRestriccion.DISPONIBLE &&
                        restriccion.getDiaSemana() == bloque.getDiaSemana() &&
                        bloquesSeSolapan(bloque.getHoraInicio(), bloque.getHoraFin(),
                                restriccion.getHoraInicio(), restriccion.getHoraFin()));
    }

    private Constraint distribuirHorariosPorDia(ConstraintFactory factory) {
        return crearRestriccionLimite(
                factory,
                this::extraerDocente,
                a -> a.getBloqueHorario().getDiaSemana(),
                a -> 1,
                MAX_CLASES_POR_DIA_DOCENTE,
                "No más de 3 clases por día por docente",
                50,
                false
        );
    }

    private Constraint evitarHuecos(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(this::extraerDocente,
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.toList())
                .penalize(HardSoftScore.ONE_SOFT.multiply(30),
                        (docente, dia, asignaciones) -> calcularHuecos(asignaciones))
                .asConstraint("Minimizar huecos entre clases");
    }

    private int calcularHuecos(List<AsignacionHorario> asignaciones) {
        if (asignaciones == null || asignaciones.size() <= 1) return 0;

        List<AsignacionHorario> filtradas = asignaciones.stream()
                .filter(this::esAsignacionValida)
                .sorted(Comparator.comparing(a -> a.getBloqueHorario().getHoraInicio()))
                .toList();

        if (filtradas.size() <= 1) return 0;

        int huecos = 0;
        for (int i = 0; i < filtradas.size() - 1; i++) {
            LocalTime finActual = filtradas.get(i).getBloqueHorario().getHoraFin();
            LocalTime inicioSiguiente = filtradas.get(i + 1).getBloqueHorario().getHoraInicio();

            Duration gap = Duration.between(finActual, inicioSiguiente);
            if (gap.toHours() > MAX_HUECOS_PERMITIDOS) {
                huecos += (int) (gap.toHours() - MAX_HUECOS_PERMITIDOS);
            }
        }
        return huecos;
    }

    private Constraint distribuirCursosPorSemana(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(a -> {
                            var csd = a.getCursoSeccionDocente();
                            return csd != null ? csd.getCurso() : null;
                        },
                        a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.count())
                .filter((curso, dia, count) -> count > 1)
                .penalize(HardSoftScore.ONE_SOFT.multiply(30), (curso, dia, count) -> count - 1)
                .asConstraint("Distribuir clases del mismo curso en diferentes días");
    }

    private Constraint incentivarDistribucionBloques(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getBloqueHorario)
                .reward(HardSoftScore.ONE_SOFT.multiply(SOFT_SCORE_HIGH))
                .asConstraint("Incentivar uso de bloques distintos");
    }

    private Constraint distribuirBloquesPorDia(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(a -> a.getBloqueHorario().getDiaSemana(),
                        ConstraintCollectors.countDistinct(AsignacionHorario::getBloqueHorario))
                .reward(HardSoftScore.ONE_SOFT.multiply(SOFT_SCORE_MEDIUM),
                        (dia, countBloques) -> countBloques)
                .asConstraint("Distribuir bloques por día");
    }

    private Constraint maximizarUsoAulas(ConstraintFactory factory) {
        return factory
                .forEach(AsignacionHorario.class)
                .groupBy(AsignacionHorario::getAula)
                .reward(HardSoftScore.ONE_SOFT.multiply(SOFT_SCORE_HIGH))
                .asConstraint("Maximizar uso de aulas diferentes");
    }
}