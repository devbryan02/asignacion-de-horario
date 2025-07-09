package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.application.dto.response.GeneracionHorarioResponse;
import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.repository.AsignacionHorarioRepository;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.optaplanner.core.api.solver.SolverJob;
import org.optaplanner.core.api.solver.SolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HorarioSolverUseCase {

    private final SolverManager<HorarioSolucion, UUID> solverManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;
    private final Logger log = LoggerFactory.getLogger(HorarioSolverUseCase.class);
    private final MensajeGeneracionUseCase mostrarMensajeGeneracionUseCase;
    private final AnalizarSolucionUseCase analizarSolucionHorarioUseCase;

    @Transactional
    public GeneracionHorarioResponse ejecutar(HorarioSolucion solucion) throws ExecutionException, InterruptedException {

        try {
            UUID solucionId = UUID.randomUUID();
            log.info("🚀 Iniciando resolución de horario con ID: {}", solucionId);

            SolverJob<HorarioSolucion, UUID> solverJob = solverManager.solve(solucionId, solucion);
            HorarioSolucion solucionFinal = solverJob.getFinalBestSolution();

            if (solucionFinal == null) {
                throw new IllegalStateException("No se pudo resolver el horario");
            }

            // 🔍 DIAGNÓSTICO COMPLETO
            List<AsignacionHorario> todasLasAsignaciones = solucionFinal.getAsignacionHorarioList();
            log.info("📊 Solución obtenida - Score: {}", solucionFinal.getScore());
            log.info("📋 Total asignaciones generadas: {}", todasLasAsignaciones.size());

            // 🔥 FILTRAR SOLO ASIGNACIONES COMPLETAS
            List<AsignacionHorario> asignacionesCompletas = todasLasAsignaciones.stream()
                    .filter(this::esAsignacionCompleta)
                    .collect(Collectors.toList());

            long asignacionesIncompletas = todasLasAsignaciones.size() - asignacionesCompletas.size();

            log.info("✅ Asignaciones completas: {}", asignacionesCompletas.size());
            log.info("❌ Asignaciones incompletas: {}", asignacionesIncompletas);

            // 🚨 VERIFICAR SI HAY VIOLACIONES HARD
            long hardScore = solucionFinal.getScore().hardScore();
            long softScore = solucionFinal.getScore().softScore();

            if (hardScore < 0) {
                log.warn("⚠️ SOLUCIÓN CON VIOLACIONES HARD - Score: {}", hardScore);
            } else {
                log.info("✅ Solución válida sin violaciones HARD");
            }

            if (asignacionesCompletas.isEmpty()) {
                log.error("🚨 CRÍTICO: No hay asignaciones completas para guardar");
                log.error("🔍 Posibles causas: Restricciones muy estrictas o recursos insuficientes");
                throw new RuntimeException("OptaPlanner no encontró solución válida - todas las asignaciones están incompletas");
            }

            // 🔥 GUARDAR SOLO ASIGNACIONES COMPLETAS
            log.info("💾 Guardando {} asignaciones completas...", asignacionesCompletas.size());
            int asignacionesGuardadas = guardarAsignacionesCompletas(asignacionesCompletas);

            // 📊 ANÁLISIS BASADO EN ASIGNACIONES COMPLETAS
            int cantidadAulasUsadas = analizarSolucionHorarioUseCase.obtenerAulasUsadas(asignacionesCompletas).size();
            int cantidadBloquesUsados = analizarSolucionHorarioUseCase.obtenerBloquesUsados(asignacionesCompletas).size();
            int cantidadDocentesAsignados = analizarSolucionHorarioUseCase.obtenerCantidadDocentesAsignados(asignacionesCompletas);

            String calidadGeneracion = mostrarMensajeGeneracionUseCase.determinarCalidadGeneracion(hardScore, softScore);
            String mensajeEvaluacion = mostrarMensajeGeneracionUseCase.generarMensajeEvaluacion(hardScore, softScore);

            log.info("🎉 Horario generado exitosamente:");
            log.info("   📝 Asignaciones guardadas: {}", asignacionesGuardadas);
            log.info("   🏫 Aulas utilizadas: {}", cantidadAulasUsadas);
            log.info("   ⏰ Bloques utilizados: {}", cantidadBloquesUsados);
            log.info("   👨‍🏫 Docentes asignados: {}", cantidadDocentesAsignados);
            log.info("   📈 Calidad: {}", calidadGeneracion);

            return new GeneracionHorarioResponse(
                    "Horario generado exitosamente",
                    asignacionesGuardadas,
                    cantidadAulasUsadas,
                    cantidadBloquesUsados,
                    cantidadDocentesAsignados,
                    calidadGeneracion,
                    mensajeEvaluacion
            );

        } catch (Exception e) {
            log.error("💥 Error resolviendo horario: {}", e.getMessage(), e);
            throw new RuntimeException("Error resolviendo horario: " + e.getMessage(), e);
        }
    }

    /**
     * 🔍 Valida si una asignación está completa (tiene aula, bloque y curso asignados)
     */
    private boolean esAsignacionCompleta(AsignacionHorario asignacion) {
        return asignacion != null &&
                asignacion.getAula() != null &&
                asignacion.getBloqueHorario() != null &&
                asignacion.getCursoSeccionDocente() != null;
    }

    /**
     * 💾 Guarda las asignaciones completas con manejo de errores individual
     *
     * @param asignaciones Lista de asignaciones completas a guardar
     * @return Número de asignaciones guardadas exitosamente
     */
    private int guardarAsignacionesCompletas(List<AsignacionHorario> asignaciones) {
        int guardadas = 0;
        int errores = 0;

        for (AsignacionHorario asignacion : asignaciones) {
            try {
                // 🔄 Resetear ID para crear nueva entidad en BD
                asignacion.setId(null);

                // 💾 El repository maneja automáticamente la conversión domain -> entity
                asignacionHorarioRepository.save(asignacion);
                guardadas++;

                if (guardadas % 10 == 0) {
                    log.debug("📝 Guardadas {} de {} asignaciones...", guardadas, asignaciones.size());
                }

            } catch (Exception e) {
                errores++;
                log.error("❌ Error guardando asignación:", e);
                log.error("   📚 Curso: {}",
                        asignacion.getCursoSeccionDocente() != null && asignacion.getCursoSeccionDocente().getCurso() != null
                                ? asignacion.getCursoSeccionDocente().getCurso().getNombre() : "N/A");
                log.error("   👨‍🏫 Docente: {}",
                        asignacion.getCursoSeccionDocente() != null && asignacion.getCursoSeccionDocente().getDocente() != null
                                ? asignacion.getCursoSeccionDocente().getDocente().getNombre() : "N/A");
                log.error("   🏫 Aula: {}",
                        asignacion.getAula() != null ? asignacion.getAula().getNombre() : "N/A");
                log.error("   ⏰ Bloque: {}",
                        asignacion.getBloqueHorario() != null
                                ? asignacion.getBloqueHorario().getDiaSemana() + " " + asignacion.getBloqueHorario().getHoraInicio()
                                : "N/A");
            }
        }

        log.info("💾 Persistencia completada:");
        log.info("   ✅ Guardadas exitosamente: {}", guardadas);
        log.info("   ❌ Errores encontrados: {}", errores);

        if (errores > 0) {
            log.warn("⚠️ Se encontraron {} errores durante la persistencia", errores);
            log.warn("💡 Revisa las validaciones de integridad referencial en la BD");
        }

        return guardadas;
    }
}