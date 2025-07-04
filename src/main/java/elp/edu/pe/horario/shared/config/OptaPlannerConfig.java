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
                .withTerminationSpentLimit(java.time.Duration.ofMinutes(2)) // Tiempo máximo de ejecución
                .withPhases(
                        new ConstructionHeuristicPhaseConfig(),  // Fase de construcción inicial
                        new LocalSearchPhaseConfig()// Fase de búsqueda local para optimizar
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
