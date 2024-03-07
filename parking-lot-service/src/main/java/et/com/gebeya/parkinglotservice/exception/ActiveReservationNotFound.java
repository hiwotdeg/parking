package et.com.gebeya.parkinglotservice.exception;

public class ActiveReservationNotFound extends RuntimeException{
    public ActiveReservationNotFound(String message){
        super(message);
    }
}
