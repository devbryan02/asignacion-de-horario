package elp.edu.pe.horario.application.usecase.docente;

import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.application.dto.response.RegistroResponse;
import elp.edu.pe.horario.application.mapper.DocenteDtoMapper;
import elp.edu.pe.horario.domain.model.Docente;
import elp.edu.pe.horario.domain.model.UnidadAcademica;
import elp.edu.pe.horario.domain.repository.DocenteRepository;
import elp.edu.pe.horario.domain.repository.UnidadRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CrearDocenteUseCase {

    private static final Logger log = LoggerFactory.getLogger(CrearDocenteUseCase.class);
    private final DocenteRepository docenteRepository;
    private final DocenteDtoMapper mapper;
    private final UnidadRepository unidadRepository;

    @Transactional
    public RegistroResponse ejecutar(DocenteRequest request) {
        try{
            Docente docente = mapper.toDomain(request);

            List<UUID> unidadesIds = request.unidadesIds();
            if(unidadesIds != null && !unidadesIds.isEmpty()){
                List<UnidadAcademica> unidades = unidadRepository.findAllById(unidadesIds);
                if (unidades.size() != unidadesIds.size()) {
                    return RegistroResponse.failure("Una o más unidades no existen");
                }
                docente.setUnidadesAcademicas(unidades);
            }
            docenteRepository.save(docente);
            log.info("Docente y unidades académicas asociadas creados correctamente: {}", docente);
            return RegistroResponse.success("Docente creado correctamente");
        }catch (Exception e){
            log.error("Error al crear el docente", e);
            return RegistroResponse.failure("Error al crear el docente");
        }
    }
}
