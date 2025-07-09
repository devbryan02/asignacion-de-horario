package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.model.*;
import elp.edu.pe.horario.domain.repository.*;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HorarioSolucionBuilderImpl implements HorarioSolucionBuilder {

    private static final Logger log = LoggerFactory.getLogger(HorarioSolucionBuilderImpl.class);
    private static final int HORAS_POR_BLOQUE = 2;
    private static final double FACTOR_DISTRIBUCION_BLOQUES = 3.0;

    private final AulaRepository aulaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final CursoSeccionDocenteRepository cursoSeccionDocenteRepository;
    private final RestriccionDocenteRepository restriccionDocenteRepository;
    private final EntityManager entityManager;
    private final AsignacionHorarioRepository asignacionHorarioRepository;

    @Transactional
    @Override
    public HorarioSolucion construirDesdeBaseDeDatos(UUID periodoId) {
        log.info("Iniciando construcción de solución de horario para periodo: {}", periodoId);

        HorarioSolucion solucion = new HorarioSolucion();

        // Limpiar asignaciones previas
        limpiarAsignacionesPrevias();

        // Cargar datos desde la base de datos
        List<Aula> aulas = aulaRepository.findByPeriodoId(periodoId);
        List<BloqueHorario> bloques = bloqueHorarioRepository.findByPeriodoId(periodoId);
        List<CursoSeccionDocente> cursoSeccionDocentes = cursoSeccionDocenteRepository.findByPeriodoId(periodoId);

        // Validar datos básicos
        validarDatosBasicos(aulas, bloques, cursoSeccionDocentes);

        // Cargar restricciones para docentes
        cargarRestriccionesParaDocentes(cursoSeccionDocentes);

        // Generar asignaciones basadas en horas semanales
        List<AsignacionHorario> asignacionesIniciales = generarAsignacionesBasadasEnHoras(cursoSeccionDocentes);

        // Validar recursos con el número real de asignaciones
        validarRecursosParaAsignaciones(aulas, bloques, asignacionesIniciales);

        // Configurar la solución
        solucion.setAulaList(aulas);
        solucion.setBloqueHorarioList(bloques);
        solucion.setCursoSeccionDocentes(cursoSeccionDocentes);
        solucion.setAsignacionHorarioList(asignacionesIniciales);

        log.info("Solución de horario construida exitosamente con {} asignaciones",
                asignacionesIniciales.size());

        return solucion;
    }

    @Transactional
    void limpiarAsignacionesPrevias() {
        log.debug("Limpiando asignaciones previas...");
        entityManager.clear();
        asignacionHorarioRepository.deleteAllInBatch();
        entityManager.flush();
    }

    private void validarDatosBasicos(List<Aula> aulas, List<BloqueHorario> bloques,
                                     List<CursoSeccionDocente> cursoSeccionDocentes) {
        if (aulas.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron aulas para el período especificado");
        }
        if (bloques.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron bloques horarios para el período especificado");
        }
        if (cursoSeccionDocentes.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron asignaciones curso-sección-docente para el período especificado");
        }
    }

    private void validarRecursosParaAsignaciones(List<Aula> aulas, List<BloqueHorario> bloques,
                                                 List<AsignacionHorario> asignaciones) {
        int totalAsignaciones = asignaciones.size();
        int bloquesNecesarios = (int) Math.ceil(totalAsignaciones / FACTOR_DISTRIBUCION_BLOQUES);

        if (bloques.size() < bloquesNecesarios) {
            throw new IllegalStateException(
                    String.format("Se necesitan al menos %d bloques para %d asignaciones. Actuales: %d",
                            bloquesNecesarios, totalAsignaciones, bloques.size())
            );
        }

        int aulasNecesarias = (int) Math.ceil((double) totalAsignaciones / bloques.size());
        if (aulas.size() < aulasNecesarias) {
            throw new IllegalStateException(
                    String.format("Se necesitan al menos %d aulas para %d asignaciones. Actuales: %d",
                            aulasNecesarias, totalAsignaciones, aulas.size())
            );
        }

        log.info("Validación de recursos completada -> Aulas: {}, Bloques: {}, " +
                        "Total Asignaciones: {}, Bloques mínimos: {}, Aulas mínimas: {}",
                aulas.size(), bloques.size(), totalAsignaciones, bloquesNecesarios, aulasNecesarias);
    }

    private void cargarRestriccionesParaDocentes(List<CursoSeccionDocente> cursoSeccionDocentes) {
        log.debug("Cargando restricciones para docentes...");

        Set<Docente> docentesUnicos = cursoSeccionDocentes.stream()
                .map(CursoSeccionDocente::getDocente)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // TODO: Optimizar con una sola query usando IDs
        for (Docente docente : docentesUnicos) {
            List<RestriccionDocente> restricciones =
                    restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            docente.setRestricciones(restricciones);

            log.debug("Docente {} tiene {} restricciones cargadas",
                    docente.getNombre(), restricciones.size());
        }

        log.info("Restricciones cargadas para {} docentes únicos", docentesUnicos.size());
    }

    private List<AsignacionHorario> generarAsignacionesBasadasEnHoras(List<CursoSeccionDocente> cursoSeccionDocentes) {
        log.debug("Generando asignaciones basadas en horas semanales...");

        List<AsignacionHorario> asignaciones = new ArrayList<>();
        int totalAsignacionesGeneradas = 0;

        for (CursoSeccionDocente csd : cursoSeccionDocentes) {
            Integer horasSemanales = csd.getCurso().getHorasSemanales();

            // Validación y valor por defecto
            if (horasSemanales == null || horasSemanales <= 0) {
                log.warn("Curso '{}' tiene horas semanales inválidas: {}. Se asignan {} horas por defecto.",
                        csd.getCurso().getNombre(), horasSemanales, HORAS_POR_BLOQUE);
                horasSemanales = HORAS_POR_BLOQUE;
            }

            // Calcular bloques necesarios
            int bloquesNecesarios = (int) Math.ceil((double) horasSemanales / HORAS_POR_BLOQUE);

            log.debug("Curso: '{}' ({} horas semanales) generará {} asignaciones",
                    csd.getCurso().getNombre(), horasSemanales, bloquesNecesarios);

            // Crear asignaciones para cada bloque necesario
            for (int i = 0; i < bloquesNecesarios; i++) {
                AsignacionHorario asignacion = new AsignacionHorario(
                        UUID.randomUUID(),
                        csd,
                        null,
                        null
                );

                asignaciones.add(asignacion);
                totalAsignacionesGeneradas++;
            }
        }

        log.info("Generadas {} asignaciones en total para {} cursos-sección-docente",
                totalAsignacionesGeneradas, cursoSeccionDocentes.size());

        return asignaciones;
    }
}