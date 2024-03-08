package et.com.gebeya.parkinglotservice.exception;

public class CancelReservationException extends RuntimeException{
    public CancelReservationException(String message){
        super(message);
    }
}
