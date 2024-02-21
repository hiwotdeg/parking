package et.com.gebeya.parkinglotservice.exception;

import et.com.gebeya.parkinglotservice.dto.requestdto.ErrorMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(ParkingLotIdNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, Object>> handleParkingLotIdNotFound(ParkingLotIdNotFound exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Map<String, Object>> handleAuthService(AuthException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<Map<String, Object>> handleWebClientResponseException(WebClientResponseException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", "error occurred. please try again later");
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MoreThanOneProviderException.class)
    public ResponseEntity<Map<String, Object>> handleMoreThanOneProviderException(MoreThanOneProviderException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DriverIdNotFound.class)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleException(DriverIdNotFound e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(ProviderIdNotFound.class)
    public ResponseEntity<Map<String, Object>> handleProviderIdNotFound(ProviderIdNotFound exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        log.error(exception.getClass().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception exception) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
