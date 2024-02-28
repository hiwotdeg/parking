package et.com.gebeya.parkinglotservice.exception;

public class OperationHourIdNotFound extends RuntimeException{
    public OperationHourIdNotFound(String message){
        super(message);
    }
}
