package et.com.gebeya.authservice.exception;

import et.com.gebeya.authservice.dto.response_dto.ErrorMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorResponse = ErrorMessage.builder().message("data integrity violation. please use different value").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.append(fieldName).append(" ").append(errorMessage).append("\n");
        });
        return ErrorMessage.builder().message(errorMessages.toString()).build();
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMalformedJwtException(MalformedJwtException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(UnsupportedJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleUnsupportedJwtExceptionException(UnsupportedJwtException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
    @ExceptionHandler(SignatureException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleSignatureExceptionException(SignatureException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleExpiredJwtExceptionException(ExpiredJwtException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleJwtExceptionException(JwtException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleJwtExceptionException(Exception e) {
        log.error(e.getMessage(),e);
        ErrorMessage errorMessage = ErrorMessage.builder().message(e.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
