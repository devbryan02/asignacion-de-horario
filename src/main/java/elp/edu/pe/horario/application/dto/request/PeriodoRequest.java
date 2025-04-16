package elp.edu.pe.horario.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public record PeriodoRequest(
    String nombre,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaInicio,
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate fechaFin
) { }
