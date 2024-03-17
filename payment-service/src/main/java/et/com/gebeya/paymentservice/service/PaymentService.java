package et.com.gebeya.paymentservice.service;


import et.com.gebeya.paymentservice.dto.request.CreditOrDebitMessageDto;
import et.com.gebeya.paymentservice.dto.request.MpesaB2CResponse;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.response.VerifyResponse;
import et.com.gebeya.paymentservice.enums.PaymentStatus;
import et.com.gebeya.paymentservice.enums.PaymentType;
import et.com.gebeya.paymentservice.exception.PaymentException;
import et.com.gebeya.paymentservice.model.Payment;
import et.com.gebeya.paymentservice.repository.PaymentRepository;
import et.com.gebeya.paymentservice.repository.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static et.com.gebeya.paymentservice.util.Constant.CREDIT_OR_DEBIT_MESSAGE;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentGateway paymentGateway;
    private final BalanceService balanceService;
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, CreditOrDebitMessageDto> creditOrDebitMessageDtoKafkaTemplate;

    @Async
    public void initiateDeposit(String userId, String phoneNo,Integer amount){
        String checkoutId = paymentGateway.initiateDeposit(phoneNo,amount);

        if(checkoutId==null)
            throw new PaymentException("error occurred during requesting payment");
        Payment payment = Payment.builder().paymentType(PaymentType.CREDIT).paymentStatus(PaymentStatus.PENDING).checkoutId(checkoutId).userId(userId).amount(amount).build();
        paymentRepository.save(payment);
    }

    @Async
    public void confirmDeposit(MpesaStkCallback request){
        VerifyResponse confirmDepositForMpesaResponse = paymentGateway.confirmDeposit(request);
        if(confirmDepositForMpesaResponse.getCheckoutId()==null)
            throw new PaymentException("error occurred during confirming payment");

        Payment payment = getPayment(confirmDepositForMpesaResponse.getCheckoutId(), PaymentType.CREDIT);
        if(Boolean.TRUE.equals(confirmDepositForMpesaResponse.getIsSuccessful())){
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            balanceService.depositBalance(payment.getUserId(), BigDecimal.valueOf(payment.getAmount()));
            CreditOrDebitMessageDto creditOrDebitMessageDto = CreditOrDebitMessageDto.builder().amount(BigDecimal.valueOf(payment.getAmount())).userId(payment.getUserId()).build();
            creditOrDebitMessageDtoKafkaTemplate.send(CREDIT_OR_DEBIT_MESSAGE,creditOrDebitMessageDto);
        }
        else{
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
        }
    }

    @Async
    public void initiateWithdraw(String userId, String phoneNo,Integer amount){
        balanceService.withdrawalBalanceChecker(userId, BigDecimal.valueOf(amount));
        String checkoutId = paymentGateway.initiateWithdrawal(phoneNo,amount);
        if(checkoutId==null)
            throw new PaymentException("error occurred during requesting payment");
        Payment payment = Payment.builder().amount(amount).paymentStatus(PaymentStatus.PENDING).paymentType(PaymentType.DEBIT).checkoutId(checkoutId).userId(userId).build();
        paymentRepository.save(payment);
    }

    @Async
    public void confirmWithdraw(MpesaB2CResponse request){
        VerifyResponse conformWithdrawalForMpesaResponse = paymentGateway.confirmWithdrawal(request);
        if(conformWithdrawalForMpesaResponse.getCheckoutId()==null)
            throw new PaymentException("error occurred during conforming payment");

        Payment payment = getPayment(conformWithdrawalForMpesaResponse.getCheckoutId(), PaymentType.DEBIT);
        if(conformWithdrawalForMpesaResponse.getIsSuccessful()){
            payment.setPaymentStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);
            balanceService.withdrawalBalance(payment.getUserId(), BigDecimal.valueOf(payment.getAmount()));
            CreditOrDebitMessageDto creditOrDebitMessageDto = CreditOrDebitMessageDto.builder().amount(BigDecimal.valueOf(payment.getAmount())).userId(payment.getUserId()).build();
            creditOrDebitMessageDtoKafkaTemplate.send(CREDIT_OR_DEBIT_MESSAGE,creditOrDebitMessageDto);
        }
        else{
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
        }
    }


    private Payment getPayment(String checkoutId, PaymentType paymentType) {
        List<Payment> payments = paymentRepository.findAll(PaymentSpecification.withPaymentStatusAndTypeAndCheckoutId(PaymentStatus.PENDING, paymentType, checkoutId));
        if (payments.isEmpty())
            throw new PaymentException("id not found");
        return payments.get(0);
    }


}
