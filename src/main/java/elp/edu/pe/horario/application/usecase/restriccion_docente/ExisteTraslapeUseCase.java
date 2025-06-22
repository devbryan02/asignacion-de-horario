package elp.edu.pe.horario.application.usecase.restriccion_docente;

import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
@Service
public class ExisteTraslapeUseCase {

    private final Logger log = LoggerFactory.getLogger(ExisteTraslapeUseCase.class);

    public boolean execute(RestriccionDocente existente, RestriccionRequest nueva){

        LocalTime nuevaHoraInicio = nueva.horaInicio();
        LocalTime nuevaHoraFin = nueva.horaFin();
        LocalTime horaInicioExistente = existente.getHoraInicio();
        LocalTime horaFinExistente = existente.getHoraFin();

        // Convertir el día de la solicitud al enum DiaSemana
        DiaSemana diaSemanaNueva;
        try {
            diaSemanaNueva = DiaSemana.valueOf(nueva.diaSemana().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Día de la semana no válido en la solicitud: {}", nueva.diaSemana());
            return false; // Si no es válido, no hay traslape
        }

        // Realizar la comparación con el día correctamente convertido
        return existente.getDiaSemana().equals(diaSemanaNueva) &&
                nuevaHoraInicio.isBefore(horaFinExistente) &&
                nuevaHoraFin.isAfter(horaInicioExistente);
    }
}
