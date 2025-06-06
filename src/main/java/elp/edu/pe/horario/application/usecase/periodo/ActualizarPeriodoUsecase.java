package elp.edu.pe.horario.application.usecase.periodo;

import elp.edu.pe.horario.application.dto.request.PeriodoRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.PeriodoDtoMapper;
import elp.edu.pe.horario.domain.model.PeriodoAcademico;
import elp.edu.pe.horario.domain.repository.PeriodoRepository;
import elp.edu.pe.horario.shared.exception.BadRequest;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActualizarPeriodoUsecase {

    private final static Logger log = LoggerFactory.getLogger(ActualizarPeriodoUsecase.class);
    private final PeriodoRepository periodoRepository;
    private final PeriodoDtoMapper mapper;

    public RegistroResponse ejecutar(PeriodoRequest nuevo , UUID periodoId) {

        try{
            // Validar que el ID no sea nulo
            if (periodoId == null) {
                throw new BadRequest("ID no puede ser nulo");
            }

            // Buscar el periodo por ID
            PeriodoAcademico periodoExistente = periodoRepository.findById(periodoId)
                    .orElseThrow(() -> new NotFoundException("Periodo no encontrado"));

            // Actualizar los campos del periodo con los datos del request
            mapper.updateDomain(periodoExistente, nuevo);

            // Guardar el periodo actualizado
            periodoRepository.actualizar(periodoExistente);
            log.info("Periodo actualizado: {}", periodoExistente);
            return RegistroResponse.success("Periodo actualizado correctamente");
        }catch (Exception e) {
            // Manejar excepciones específicas
            throw new BadRequest("Error al actualizar el periodo: " + e.getMessage());
        }

    }

}
