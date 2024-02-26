package et.com.gebeya.parkinglotservice.exception;

public class VehicleIdNotFound extends RuntimeException{
    public VehicleIdNotFound(String message){
        super(message);
    }
}
