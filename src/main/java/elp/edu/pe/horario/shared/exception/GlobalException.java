package elp.edu.pe.horario.shared.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception e) {
        log.error("Error no controlado: ", e);
        Map<String, String> response = new HashMap<>();
        response.put("error", e.getMessage());
        response.put("tipo", e.getClass().getSimpleName());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}