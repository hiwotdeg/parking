package et.com.gebeya.parkinglotservice.exception;

public class MultipleReviewException extends RuntimeException{
    public MultipleReviewException(String message){
        super(message);
    }
}
