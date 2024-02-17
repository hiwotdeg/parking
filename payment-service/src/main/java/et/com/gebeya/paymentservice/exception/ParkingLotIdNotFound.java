package et.com.gebeya.paymentservice.exception;

public class ParkingLotIdNotFound extends RuntimeException{
    public ParkingLotIdNotFound(String message){
        super(message);
    }
}
