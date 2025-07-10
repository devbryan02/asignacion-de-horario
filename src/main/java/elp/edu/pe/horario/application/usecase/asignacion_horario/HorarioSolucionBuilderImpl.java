package elp.edu.pe.horario.application.usecase.asignacion_horario;

import elp.edu.pe.horario.domain.model.*;
import elp.edu.pe.horario.domain.repository.*;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import elp.edu.pe.horario.domain.solver.HorarioSolucionBuilder;
import elp.edu.pe.horario.infrastructure.persistence.entity.DocenteEntity;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class HorarioSolucionBuilderImpl implements HorarioSolucionBuilder {

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

        HorarioSolucion solucion = new HorarioSolucion();

        limpiarAsignacionesPrevias();

        List<Aula> aulas = aulaRepository.findByPeriodoId(periodoId);
        List<BloqueHorario> bloques = bloqueHorarioRepository.findByPeriodoId(periodoId);
        List<CursoSeccionDocente> cursoSeccionDocentes = cursoSeccionDocenteRepository.findByPeriodoId(periodoId);

        verificarAsignacionDeDocentes(periodoId);

        validarDatosBasicos(aulas, bloques, cursoSeccionDocentes);
        cargarRestriccionesParaDocentes(cursoSeccionDocentes);
        List<AsignacionHorario> asignacionesIniciales = generarAsignacionesBasadasEnHoras(cursoSeccionDocentes);
        validarRecursosParaAsignaciones(aulas, bloques, asignacionesIniciales);

        solucion.setAulaList(aulas);
        solucion.setBloqueHorarioList(bloques);
        solucion.setCursoSeccionDocentes(cursoSeccionDocentes);
        solucion.setAsignacionHorarioList(asignacionesIniciales);


        return solucion;
    }

    @Transactional
    void limpiarAsignacionesPrevias() {
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
        int aulasNecesarias = (int) Math.ceil((double) totalAsignaciones / bloques.size());

        if (bloques.size() < bloquesNecesarios) {
            throw new IllegalStateException(String.format(
                    "Se necesitan al menos %d bloques para %d asignaciones. Actuales: %d",
                    bloquesNecesarios, totalAsignaciones, bloques.size()));
        }

        if (aulas.size() < aulasNecesarias) {
            throw new IllegalStateException(String.format(
                    "Se necesitan al menos %d aulas para %d asignaciones. Actuales: %d",
                    aulasNecesarias, totalAsignaciones, aulas.size()));
        }
    }

    private void cargarRestriccionesParaDocentes(List<CursoSeccionDocente> cursoSeccionDocentes) {
        Set<Docente> docentesUnicos = cursoSeccionDocentes.stream()
                .map(CursoSeccionDocente::getDocente)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (Docente docente : docentesUnicos) {
            List<RestriccionDocente> restricciones =
                    restriccionDocenteRepository.findAllByDocenteId(docente.getId());
            docente.setRestricciones(restricciones);
        }
    }

    private List<AsignacionHorario> generarAsignacionesBasadasEnHoras(List<CursoSeccionDocente> cursoSeccionDocentes) {
        List<AsignacionHorario> asignaciones = new ArrayList<>();

        for (CursoSeccionDocente csd : cursoSeccionDocentes) {
            Integer horasSemanales = csd.getCurso().getHorasSemanales();
            if (horasSemanales == null || horasSemanales <= 0) {
                horasSemanales = HORAS_POR_BLOQUE;
            }

            int bloquesNecesarios = (int) Math.ceil((double) horasSemanales / HORAS_POR_BLOQUE);

            for (int i = 0; i < bloquesNecesarios; i++) {
                asignaciones.add(new AsignacionHorario(
                        UUID.randomUUID(),
                        csd,
                        null,
                        null
                ));
            }
        }

        return asignaciones;
    }

    private void verificarAsignacionDeDocentes(UUID periodoId) {
        // Obtenemos todos los IDs de docentes
        List<UUID> idsTodosLosDocentes = entityManager.createQuery(
                "SELECT d.id FROM DocenteEntity d", UUID.class).getResultList();

        // Obtenemos los IDs de docentes que están en CursoSeccionDocente
        List<UUID> idsDocentesAsignados = entityManager.createQuery(
                        "SELECT DISTINCT csd.docente.id FROM CursoSeccionDocenteEntity csd " +
                                "WHERE csd.seccion.periodo.id = :periodoId", UUID.class)
                .setParameter("periodoId", periodoId)
                .getResultList();

        Set<UUID> asignadosSet = new HashSet<>(idsDocentesAsignados);

        // Listamos los docentes NO asignados
        List<DocenteEntity> noAsignados = entityManager.createQuery(
                        "SELECT d FROM DocenteEntity d WHERE d.id NOT IN :idsAsignados", DocenteEntity.class)
                .setParameter("idsAsignados", asignadosSet)
                .getResultList();

        log.info("🧑‍🏫 Total de docentes registrados: {}", idsTodosLosDocentes.size());
        log.info("✅ Docentes asignados a curso-sección: {}", asignadosSet.size());
        log.info("❌ Docentes NO asignados:");
        noAsignados.forEach(d -> log.info("- {} ", d.getNombre()));
    }


}
