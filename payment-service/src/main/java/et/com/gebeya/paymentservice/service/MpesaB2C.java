package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.*;
import et.com.gebeya.paymentservice.dto.response.B2cResponse;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.enums.PaymentStatus;
import et.com.gebeya.paymentservice.enums.PaymentType;
import et.com.gebeya.paymentservice.model.Payment;
import et.com.gebeya.paymentservice.repository.PaymentRepository;
import et.com.gebeya.paymentservice.repository.specification.PaymentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpesaB2C {
    private final PaymentRepository paymentRepository;
    private final WebClient.Builder webClientBuilder;
    private final CouponManagementService couponManagementService;
    private final MpesaLogin mpesaLogin;

    @Value("${mpesa.b2c.initiator}")
    private String initiator;
    @Value("${mpesa.b2c.securityCredential}")
    private String securityCredential;
    @Value("${mpesa.b2c.sender}")
    private String sender;
    @Value("${mpesa.b2c.resultUrl}")
    private String resultUrl;

    private Mono<B2cResponse> withdrawalRequester(B2cRequest request, String authorization) {
        return webClientBuilder.build()
                .post()
                .uri("https://apisandbox.safaricom.et/mpesa/b2c/v1/paymentrequest")
                .header("Authorization", authorization)
                .bodyValue(request)
                .retrieve().bodyToMono(B2cResponse.class);
    }

    @Async
    public void withdrawalTask(String userId, WithdrawDto dto) {
        try {
            String token = mpesaLogin.login();
            log.info("login completed");
            B2cRequest request = B2cRequest.builder()
                    .amount(dto.getAmount())
                    .commandID("BusinessPayment")
                    .initiatorName(initiator)
                    .occassion("Parking")
                    .partyA(sender)
                    .partyB(dto.getPhoneNo())
                    .queueTimeOutURL("https://www.myservice:8080/b2c/result")
                    .remarks("Parking")
                    .resultURL(resultUrl)
                    .securityCredential(securityCredential).build();
            B2cResponse response = withdrawalRequester(request, token).block();
            assert response != null;
            log.info(response.toString());
            if (!response.getResponseCode().equals("0"))
                throw new RuntimeException("error occurred during request deposit");
            log.info("request completed");
            Payment payment = Payment.builder().paymentStatus(PaymentStatus.PENDING).paymentType(PaymentType.DEBIT).amount(dto.getAmount()).userId(userId).checkoutId(response.getOriginatorConversationID()).build();
            paymentRepository.save(payment);
        } catch (RuntimeException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    private Payment getPayment(String checkoutId) {
        List<Payment> payments = paymentRepository.findAll(PaymentSpecification.withPaymentStatusAndTypeAndCheckoutId(PaymentStatus.PENDING, PaymentType.DEBIT, checkoutId));
        if (payments.isEmpty())
            throw new RuntimeException("id not found");
        return payments.get(0);
    }

    @Async
    public void verifyWithdraw(MpesaB2CResponse responseBody) {
        try {
            if (responseBody.getResult().getResultCode() == 0) {
                log.info(responseBody.toString());
                Payment payment = getPayment(responseBody.getResult().getOriginatorConversationID());
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment = paymentRepository.save(payment);
                String[] userId = payment.getUserId().split("_");
                BalanceResponseDto dto = couponManagementService.withdrawalBalanceForProvider(BalanceRequestDto.builder().amount(BigDecimal.valueOf(payment.getAmount())).userId(Integer.valueOf(userId[1])).build());
                log.info("verify withdrawal response {}", dto.toString());
            } else {
                log.error(responseBody.toString());
                Payment payment = getPayment(responseBody.getResult().getOriginatorConversationID());
                payment.setPaymentStatus(PaymentStatus.FAILED);
                paymentRepository.save(payment);
                log.error("verify deposit response {}",payment);
            }

        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }


}
