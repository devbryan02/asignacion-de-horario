package elp.edu.pe.horario.application.usecase.restriccion_docente;

import elp.edu.pe.horario.application.dto.request.RestriccionRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.RestriccionDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.RestriccionDocente;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.domain.repository.RestriccionDocenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CrearRestriccionesBatchUseCase {

    private final RestriccionDocenteRepository restriccionDocenteRepository;
    private final RestriccionDtoMapper mapper;
    private final DocenteRepository docenteRepository;
    private final ExisteTraslapeUseCase existeTraslapeUseCase;

    public RegistroResponse execute(List<RestriccionRequest> restriccionesRequest) {
        try {
            Docente docente = docenteRepository.findById(restriccionesRequest.get(0).docenteId())
                    .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

            // Get existing restrictions for this docente
            List<RestriccionDocente> restriccionesExistentes = docente.getRestricciones();

            // Check for overlaps
            for (RestriccionRequest nuevaRestriccion : restriccionesRequest) {
                for (RestriccionDocente restriccionExistente : restriccionesExistentes) {
                    if (existeTraslapeUseCase.execute(restriccionExistente, nuevaRestriccion)) {
                        //mensaje mas detallado
                        String mensaje = String.format(
                                "Conflicto con restricción existente: Día %s de %s a %s (Tipo: %s).",
                                restriccionExistente.getDiaSemana().name(),
                                restriccionExistente.getHoraInicio().toString(),
                                restriccionExistente.getHoraFin().toString(),
                                restriccionExistente.getTipoRestriccion().name()
                        );
                        return RegistroResponse.failure(mensaje);
                    }
                }
            }

            // If no overlaps, proceed with saving
            List<RestriccionDocente> restricciones = restriccionesRequest.stream()
                    .map(request -> mapper.toDomain(request, docente))
                    .toList();

            restriccionDocenteRepository.saveAll(restricciones);
            log.info("Restricciones creadas para el docente: {}", docente.getNombre());
            return RegistroResponse.success(restricciones.size()+" Restricciones cargadas correctamente");
        } catch (Exception e) {
            log.error(e.getMessage());
            return RegistroResponse.failure("Error al crear las restricciones: " + e.getMessage());
        }
    }
}