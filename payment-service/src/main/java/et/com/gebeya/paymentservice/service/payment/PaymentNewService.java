package et.com.gebeya.paymentservice.service.payment;


import et.com.gebeya.paymentservice.dto.request.MpesaB2CResponse;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.response.VerifyResponse;
import et.com.gebeya.paymentservice.enums.PaymentStatus;
import et.com.gebeya.paymentservice.enums.PaymentType;
import et.com.gebeya.paymentservice.model.Payment;
import et.com.gebeya.paymentservice.repository.PaymentRepository;
import et.com.gebeya.paymentservice.repository.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

// paymentService is @Async and paymentTransactionService @Transaction
//@Service
//@RequiredArgsConstructor
//public class PaymentNewService {
//    private final PaymentGateway paymentGateway;
//    private final BalanceNewService balanceNewService;
//    private final PaymentRepository paymentRepository;
//    @Async
//    public void initiateDeposit(String userId, String phoneNo,Integer amount){
//        String checkoutId = paymentGateway.initiateDepositForMpesa(phoneNo,amount);
//        if(checkoutId==null)
//            throw new RuntimeException("error occurred during requesting payment");
//        Payment payment = Payment.builder().paymentType(PaymentType.CREDIT).paymentStatus(PaymentStatus.PENDING).checkoutId(checkoutId).userId(userId).amount(amount).build();
//        paymentRepository.save(payment);
//    }
//
//    @Async
//    public void confirmDeposit(MpesaStkCallback request){
//        VerifyResponse confirmDepositForMpesaResponse = paymentGateway.confirmDepositForMpesa(request);
//        if(confirmDepositForMpesaResponse.getCheckoutId()==null)
//            throw new RuntimeException("error occurred during confirming payment");
//
//        Payment payment = getPayment(confirmDepositForMpesaResponse.getCheckoutId(), PaymentType.CREDIT,PaymentStatus.PENDING);
//        if(confirmDepositForMpesaResponse.getIsSuccessful()){
//            payment.setPaymentStatus(PaymentStatus.SUCCESS);
//            paymentRepository.save(payment);
//            balanceNewService.depositBalance(payment.getUserId(), BigDecimal.valueOf(payment.getAmount()));
//        }
//        else{
//            payment.setPaymentStatus(PaymentStatus.FAILED);
//            paymentRepository.save(payment);
//        }
//    }
//
//    @Async
//    public void initiateWithdraw(String userId, String phoneNo,Integer amount){
//        balanceNewService.withdrawalBalanceChecker(userId, BigDecimal.valueOf(amount));
//        String checkoutId = paymentGateway.initiateWithdrawalForMpesa(phoneNo,amount);
//        if(checkoutId==null)
//            throw new RuntimeException("error occurred during requesting payment");
//        Payment payment = Payment.builder().amount(amount).paymentStatus(PaymentStatus.PENDING).paymentType(PaymentType.DEBIT).checkoutId(checkoutId).userId(userId).build();
//        paymentRepository.save(payment);
//    }
//
//    @Async
//    public void confirmWithdraw(MpesaB2CResponse request){
//        VerifyResponse conformWithdrawalForMpesaResponse = paymentGateway.confirmWithdrawalForMpesa(request);
//        if(conformWithdrawalForMpesaResponse.getCheckoutId()==null)
//            throw new RuntimeException("error occurred during conforming payment");
//
//        Payment payment = getPayment(conformWithdrawalForMpesaResponse.getCheckoutId(), PaymentType.DEBIT,PaymentStatus.PENDING);
//        if(conformWithdrawalForMpesaResponse.getIsSuccessful()){
//            payment.setPaymentStatus(PaymentStatus.SUCCESS);
//            paymentRepository.save(payment);
//            balanceNewService.withdrawalBalance(payment.getUserId(), BigDecimal.valueOf(payment.getAmount()));
//        }
//        else{
//            payment.setPaymentStatus(PaymentStatus.FAILED);
//            paymentRepository.save(payment);
//        }
//    }
//
//
//    private Payment getPayment(String checkoutId, PaymentType paymentType,PaymentStatus paymentStatus) {
//        List<Payment> payments = paymentRepository.findAll(PaymentSpecification.withPaymentStatusAndTypeAndCheckoutId(paymentStatus, paymentType, checkoutId));
//        if (payments.isEmpty())
//            throw new RuntimeException("id not found");
//        return payments.get(0);
//    }
//
//
//}
