package et.com.gebeya.apigateway.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static et.com.gebeya.apigateway.util.Constants.UNEXPECTED_ERROR;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(HeaderNotFound.class)
    public ResponseEntity<Map<String, Object>> handleHeaderNotFound(HeaderNotFound exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", UNEXPECTED_ERROR);
        log.error(exception.getMessage(),exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
