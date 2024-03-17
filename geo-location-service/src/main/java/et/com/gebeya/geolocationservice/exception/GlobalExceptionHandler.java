package et.com.gebeya.geolocationservice.exception;

import et.com.gebeya.geolocationservice.dto.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import redis.clients.jedis.exceptions.JedisDataException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(JedisDataException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleInvalidLocationException(JedisDataException exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(JsonParsingException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleJsonParsingException(JsonParsingException exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("Unexpected error occurred. please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
