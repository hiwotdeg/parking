package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.response.VerifyResponse;

public interface PaymentGateway {
    String initiateDeposit(String phoneNo, double amount);

    VerifyResponse confirmDeposit(Object confirmationResponse);

    String initiateWithdrawal(String phoneNo, Integer amount);

    VerifyResponse confirmWithdrawal(Object confirmationResponse);
}
