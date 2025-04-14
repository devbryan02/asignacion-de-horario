package elp.edu.pe.horario.infrastructure.controller;

import elp.edu.pe.horario.application.usecase.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ia")
@RequiredArgsConstructor
public class SaludoController {

    private final HorarioService horarioService;

    @GetMapping("/preguntar")
    public ResponseEntity<String> preguntar(@RequestParam String prompt) {
        String respuesta = horarioService.generarRespuestaIA(prompt);
        return ResponseEntity.ok(respuesta);
    }
}
