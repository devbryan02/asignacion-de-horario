package elp.edu.pe.horario.application.usecase.asignacion_horario;

import org.springframework.stereotype.Service;

@Service
public class MensajeGeneracionUseCase {

    public String determinarCalidadGeneracion(long hardScore, long softScore) {
        if (hardScore < 0) {
            return "Deficiente";
        } else if (softScore < -20) {
            return "Aceptable";
        } else if (softScore < 0) {
            return "Buena";
        } else {
            return "Excelente";
        }
    }

    public String generarMensajeEvaluacion(long hardScore, long softScore) {
        if (hardScore < 0) {
            return "Se han violado restricciones importantes. Revisa la configuración de cursos, aulas o docentes.";
        } else if (softScore < -20) {
            return "Se cumplió con lo esencial, pero hay varias preferencias no respetadas.";
        } else if (softScore < 0) {
            return "Buena solución. Algunas preferencias fueron ignoradas, pero no hay conflictos graves.";
        } else {
            return "¡Horario óptimo generado sin conflictos ni preferencias ignoradas!";
        }
    }
}