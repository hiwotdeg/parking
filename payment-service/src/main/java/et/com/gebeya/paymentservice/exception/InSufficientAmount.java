package et.com.gebeya.paymentservice.exception;

public class InSufficientAmount extends RuntimeException{
    public InSufficientAmount(String message){
        super(message);
    }
}
