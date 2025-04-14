package elp.edu.pe.horario.application.usecase;

import elp.edu.pe.horario.infrastructure.generator.IHorarioIAClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HorarioService {

    private final IHorarioIAClient iaClient;
    private final String modelo;

    public HorarioService(IHorarioIAClient iaClient, @Value("${ollama.model}") String modelo) {
        this.iaClient = iaClient;
        this.modelo = modelo;
    }

    public String generarRespuestaIA(String prompt) {
        try{
            Map<String, Object> requestBody = Map.of(
                   "model",modelo,
                    "prompt", prompt,
                    "stream", false
            );

            Map<String, Object> response = iaClient.generarRespuesta(requestBody);
            Object res = response.get("response");
            return res != null ? res.toString() : "ERROR: No se pudo obtener la respuesta de IA";

        }catch (Exception e){
            return "ERROR: " + e.getMessage();
        }
    }


}
