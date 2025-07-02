package elp.edu.pe.horario.domain.solver;

import java.util.UUID;

public interface HorarioSolucionBuilder {

    HorarioSolucion construirDesdeBaseDeDatos(UUID periodoId);
}
