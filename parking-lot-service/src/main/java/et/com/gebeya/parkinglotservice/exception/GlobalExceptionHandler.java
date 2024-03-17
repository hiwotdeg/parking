package et.com.gebeya.parkinglotservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.parkinglotservice.dto.responsedto.ErrorMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ObjectMapper objectMapper;

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("data integrity violation. please use different value").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorMessage handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        StringBuilder errorMessages = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMessages.append(fieldName).append(" ").append(errorMessage).append("\n");
        });
        return ErrorMessage.builder().message(errorMessages.toString()).build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ErrorMessage handleConstraintViolationExceptions(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
        StringBuilder errorMessages = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errorMessages.append(fieldName).append(" ").append(errorMessage).append("\n");
        }

        return ErrorMessage.builder().message(errorMessages.toString()).build();
    }

    @ExceptionHandler(ParkingLotIdNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleParkingLotIdNotFound(ParkingLotIdNotFound exception) {
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(PricingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handlePricingException(PricingException exception) {
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientBalance.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleInsufficientBalanceException(InsufficientBalance exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ActiveReservationNotFound.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleActiveReservationNotFound(ActiveReservationNotFound exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ReservationUpdateAfterFiveMinuteException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleReservationUpdateAfterFiveMinuteException(ReservationUpdateAfterFiveMinuteException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(CancelReservationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleCancelReservationException(CancelReservationException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("Required request body is missing").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorMessage> handleAuthService(AuthException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(OperationHourIdNotFound.class)
    public ResponseEntity<ErrorMessage> handleOperationHourIdNotFoundException(OperationHourIdNotFound exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<ErrorMessage> handleClientErrorException(ClientErrorException exception) {
        try {
            log.error(exception.getMessage(), exception);
            ErrorMessage errorMessage = objectMapper.readValue(exception.getMessage(), ErrorMessage.class);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (JsonProcessingException e) {
            log.error(exception.getMessage(), exception);
            ErrorMessage errorMessage = ErrorMessage.builder().message("error occurred. please try again later").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

    }

    @ExceptionHandler(ParkingLotAvailabilityException.class)
    public ResponseEntity<ErrorMessage> handleParkingLotAvailabilityException(ParkingLotAvailabilityException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorMessage> handleWebClientResponseException(WebClientResponseException exception) {
        log.error("Error from WebClient - Status {}, Body {}", exception.getStatusCode(), exception.getResponseBodyAsString());
        try {
            ErrorMessage errorMessage = objectMapper.readValue(exception.getResponseBodyAsString(), ErrorMessage.class);
            return ResponseEntity.status(exception.getStatusCode()).body(errorMessage);
        } catch (JsonProcessingException e) {
            ErrorMessage errorMessage = ErrorMessage.builder().message("error occurred. please try again later").build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }

    }

    @ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<ErrorMessage> handleWebClientRequestException(WebClientRequestException exception) {
        log.error("webclient request exception {}", exception.getMessage());
        ErrorMessage errorMessage = ErrorMessage.builder().message("we couldn't deliver our service at the moment. please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(MoreThanOneProviderException.class)
    public ResponseEntity<ErrorMessage> handleMoreThanOneProviderException(MoreThanOneProviderException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(DriverIdNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleDriverIdNotFoundException(DriverIdNotFound exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(MultipleReviewException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleMultipleReviewExceptionException(MultipleReviewException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(VehicleIdNotFound.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleVehicleIdNotFoundException(VehicleIdNotFound exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


    @ExceptionHandler(ProviderIdNotFound.class)
    public ResponseEntity<ErrorMessage> handleProviderIdNotFound(ProviderIdNotFound exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message(exception.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorMessage> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("UnExpected Error occurred please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        ErrorMessage errorMessage = ErrorMessage.builder().message("UnExpected Error occurred please try again later").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }
}
