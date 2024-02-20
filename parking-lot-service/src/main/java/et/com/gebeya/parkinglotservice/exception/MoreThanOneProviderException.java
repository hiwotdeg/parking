package et.com.gebeya.parkinglotservice.exception;

public class MoreThanOneProviderException extends RuntimeException{
    public MoreThanOneProviderException(String message){
        super(message);
    }
}
