package elp.edu.pe.horario.shared.config;

import elp.edu.pe.horario.domain.model.AsignacionHorario;
import elp.edu.pe.horario.domain.solver.HorarioConstraintProvider;
import elp.edu.pe.horario.domain.solver.HorarioSolucion;
import org.optaplanner.core.api.solver.SolverFactory;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicPhaseConfig;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.localsearch.LocalSearchPhaseConfig;
import org.optaplanner.core.config.localsearch.LocalSearchType;
import org.optaplanner.core.config.solver.SolverConfig;
import org.optaplanner.core.config.solver.termination.TerminationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class OptaPlannerConfig {

    @Bean
    public SolverConfig solverConfig(){
        return new SolverConfig()
                .withSolutionClass(HorarioSolucion.class)
                .withEntityClasses(AsignacionHorario.class)
                .withConstraintProviderClass(HorarioConstraintProvider.class)
                .withTerminationConfig(new TerminationConfig()
                        .withSpentLimit(Duration.ofMinutes(2))
                        .withBestScoreLimit("0hard/*soft") // Termina si encuentra solución sin violaciones HARD
                        .withUnimprovedSpentLimit(Duration.ofSeconds(30)) // Termina si no mejora en 30s
                )
                .withPhases(
                        // Fase 1: Construcción inicial más agresiva
                        new ConstructionHeuristicPhaseConfig()
                                .withConstructionHeuristicType(ConstructionHeuristicType.FIRST_FIT_DECREASING),

                        // Fase 2: Búsqueda local con múltiples algoritmos
                        new LocalSearchPhaseConfig()
                                .withLocalSearchType(LocalSearchType.TABU_SEARCH)
                                .withTerminationConfig(new TerminationConfig()
                                        .withSpentLimit(Duration.ofMinutes(1))
                                        .withUnimprovedSpentLimit(Duration.ofSeconds(20))
                                ),

                        // Fase 3: Búsqueda local alternativa si hay tiempo
                        new LocalSearchPhaseConfig()
                                .withLocalSearchType(LocalSearchType.LATE_ACCEPTANCE)
                                .withTerminationConfig(new TerminationConfig()
                                        .withSpentLimit(Duration.ofMinutes(1))
                                        .withUnimprovedSpentLimit(Duration.ofSeconds(15))
                                )
                );
    }

    @Bean
    public SolverFactory<HorarioSolucion> solverFactory(SolverConfig solverConfig) {
        return SolverFactory.create(solverConfig);
    }

    @Bean
    public SolverManager<HorarioSolucion, UUID> solverManager(SolverFactory<HorarioSolucion> solverFactory) {
        return SolverManager.create(solverFactory);
    }
}