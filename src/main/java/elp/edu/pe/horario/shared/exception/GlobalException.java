package elp.edu.pe.horario.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    private ResponseEntity<ApiError> buildResponse(Exception e, HttpStatus status, HttpServletRequest request) {
        ApiError error = new ApiError(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                e.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(BadRequest.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequest e, HttpServletRequest request) {
        log.warn("BadRequest: {}", e.getMessage());
        return buildResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException e, HttpServletRequest request) {
        log.warn("NotFound: {}", e.getMessage());
        return buildResponse(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiError> handleCustom(CustomException e, HttpServletRequest request) {
        log.warn("CustomException: {}", e.getMessage());
        return buildResponse(e, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(DeleteException.class)
    public ResponseEntity<ApiError> handleDelete(DeleteException e, HttpServletRequest request) {
        log.error("DeleteException: {}", e.getMessage(), e);
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception e, HttpServletRequest request) {
        log.error("Unhandled exception: ", e);
        return buildResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
