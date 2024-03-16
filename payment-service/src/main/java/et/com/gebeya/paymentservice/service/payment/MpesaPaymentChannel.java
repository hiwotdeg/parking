package et.com.gebeya.paymentservice.service.payment;

import et.com.gebeya.paymentservice.dto.request.MpesaB2CResponse;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.response.VerifyResponse;

public interface MpesaPaymentChannel {
    String initiateDepositForMpesa(String phoneNo, double amount);
    VerifyResponse confirmDepositForMpesa(MpesaStkCallback confirmationResponse);
    String initiateWithdrawalForMpesa(String phoneNo, double amount);
    VerifyResponse confirmWithdrawalForMpesa(MpesaB2CResponse confirmationResponse);
}
