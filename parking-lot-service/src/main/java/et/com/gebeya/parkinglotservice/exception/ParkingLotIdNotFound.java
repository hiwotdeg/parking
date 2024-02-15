package et.com.gebeya.parkinglotservice.exception;

public class ParkingLotIdNotFound extends RuntimeException{
    public ParkingLotIdNotFound(String message){
        super(message);
    }
}
