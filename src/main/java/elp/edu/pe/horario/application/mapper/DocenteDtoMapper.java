package elp.edu.pe.horario.application.mapper;

import elp.edu.pe.horario.application.dto.DocenteDto;
import elp.edu.pe.horario.application.dto.RestriccionDto;
import elp.edu.pe.horario.application.dto.request.DocenteRequest;
import elp.edu.pe.horario.domain.model.Docente;
import org.springframework.stereotype.Component;

@Component
public class DocenteDtoMapper {

    public Docente toDomain(DocenteRequest request){
        return new Docente(
                null,
                request.nombre(),
                request.horasContratadas(),
                request.horasMaximasPorDia()
        );
    }

    public DocenteDto toDtoWithRestricciones(Docente docente){
        return new DocenteDto(
                docente.getId(),
                docente.getNombre(),
                docente.getHorasContratadas(),
                docente.getHorasMaximasPorDia(),
                docente.getRestricciones() != null ? docente.getRestricciones()
                                .stream()
                                .map(restriccion -> new RestriccionDto(
                                        restriccion.getId(),
                                        restriccion.getDiaSemana(),
                                        restriccion.getHoraInicio(),
                                        restriccion.getHoraFin(),
                                        restriccion.getTipoRestriccion()
                                ))
                                .toList() : null
        );
    }

    public void updateDocente(Docente existente, DocenteRequest nuevo) {
        existente.setNombre(nuevo.nombre());
        existente.setHorasContratadas(nuevo.horasContratadas());
        existente.setHorasMaximasPorDia(nuevo.horasMaximasPorDia());
        // No se actualizan las restricciones aquí, ya que se manejan por separado
    }
}
