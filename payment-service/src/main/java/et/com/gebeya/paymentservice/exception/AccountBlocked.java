package et.com.gebeya.paymentservice.exception;

public class AccountBlocked extends RuntimeException{
    public AccountBlocked(String message){
        super(message);
    }
}
