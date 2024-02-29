package et.com.gebeya.paymentservice.exception;

public class MinimumWithdrawalAmount extends RuntimeException{
    public MinimumWithdrawalAmount(String message){
        super(message);
    }
}
