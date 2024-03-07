package et.com.gebeya.parkinglotservice.exception;

public class ReservationUpdateAfterFiveMinuteException extends RuntimeException{
    public ReservationUpdateAfterFiveMinuteException(String message){
        super(message);
    }
}
