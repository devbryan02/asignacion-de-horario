package elp.edu.pe.horario.infrastructure.generator;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "ollamaClient", url = "${ollama.url}")
public interface IHorarioIAClient {

    @PostMapping
    Map<String, Object> generarRespuesta(@RequestBody Map<String, Object> requestBody);

}
