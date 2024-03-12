package et.com.gebeya.parkinglotservice.exception;

public class ReviewIdNotFound extends RuntimeException {
    public ReviewIdNotFound(String message) {
        super(message);
    }
}
