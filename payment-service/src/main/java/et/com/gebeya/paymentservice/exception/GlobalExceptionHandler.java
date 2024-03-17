package et.com.gebeya.paymentservice.exception;

import et.com.gebeya.paymentservice.dto.response.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountBlocked.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleAccountBlocked(AccountBlocked exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InSufficientAmount.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleInSufficientAmount(InSufficientAmount exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MinimumWithdrawalAmount.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleMinimumWithdrawalAmount(MinimumWithdrawalAmount exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("UnExpected Error occurred please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error(exception.getMessage(),exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("UnExpected Error occurred please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
