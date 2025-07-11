package elp.edu.pe.horario.application.usecase.restriccion_docente;

import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.RestriccionDtoMapper;
import elp.edu.pe.horario.domain.enums.DiaSemana;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;
import elp.edu.pe.horario.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrearRestriccionUsecase {

    private static final Logger log = LoggerFactory.getLogger(CrearRestriccionUsecase.class);
    private final RestriccionDocenteRepository restriccionRepository;
    private final RestriccionDtoMapper mapper;
    private final DocenteRepository docenteRepository;
    private final ExisteTraslapeUseCase existeTraslape;

    @Transactional
    public RegistroResponse ejecutar(RestriccionRequest request) {
        try {
            Docente docente = docenteRepository.findById(request.docenteId())
                    .orElseThrow(() -> new NotFoundException("Docente no encontrado"));

            RestriccionDocente nuevaRestriccion = mapper.toDomain(request, docente);
            List<RestriccionDocente> restriccionesExistentes = docente.getRestricciones();

            for (RestriccionDocente existente : restriccionesExistentes) {

                // Verificamos si hay traslape con la nueva restricción
                if (this.existeTraslape.execute(existente, request)) {
                    // Si hay traslape, mostramos el mensaje con los detalles
                    String mensaje = String.format(
                            "Conflicto con restricción existente: Día %s de %s a %s (Tipo: %s).",
                            existente.getDiaSemana().name(),
                            existente.getHoraInicio().toString(),
                            existente.getHoraFin().toString(),
                            existente.getTipoRestriccion().name()
                    );
                    return RegistroResponse.failure(mensaje);
                }
            }
            // Si no hay traslape, guardamos la nueva restricción
            restriccionRepository.save(nuevaRestriccion);
            log.info("Restricción creada: {}", nuevaRestriccion);
            return RegistroResponse.success("Restricción creada correctamente");
        } catch (NotFoundException e) {
            log.warn("Docente no encontrado: {}", e.getMessage());
            return RegistroResponse.failure(e.getMessage());
        } catch (Exception e) {
            log.error("Error al crear la restricción: {}", e.getMessage(), e);
            return RegistroResponse.failure("Error al crear la restricción");
        }
    }

}

