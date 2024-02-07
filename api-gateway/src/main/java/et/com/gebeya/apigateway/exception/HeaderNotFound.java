package et.com.gebeya.apigateway.exception;

public class HeaderNotFound extends RuntimeException{
    public HeaderNotFound(String message){
        super(message);
    }
}
