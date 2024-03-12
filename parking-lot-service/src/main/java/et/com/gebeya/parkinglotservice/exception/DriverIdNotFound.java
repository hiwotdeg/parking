package et.com.gebeya.parkinglotservice.exception;

public class DriverIdNotFound extends RuntimeException {
    public DriverIdNotFound(String message) {
        super(message);
    }
}
