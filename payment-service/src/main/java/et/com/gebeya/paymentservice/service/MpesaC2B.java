package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.DepositDto;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.request.StkRequest;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.dto.response.StkResponse;
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
public class MpesaC2B {
    private final PaymentRepository paymentRepository;
    private final WebClient.Builder webClientBuilder;
    private final CouponManagementService couponManagementService;
    private final MpesaLogin mpesaLogin;

    @Value("${mpesa.stk.bsc}")
    private String bsc;
    @Value("${mpesa.stk.pass}")
    private String pass;
    @Value("${mpesa.stk.callBack}")
    private String callBack;


    private Mono<StkResponse> depositRequester(StkRequest request, String authorization) {
        return webClientBuilder.build()
                .post()
                .uri("https://apisandbox.safaricom.et/mpesa/stkpush/v1/processrequest")
                .header("Authorization", authorization)
                .bodyValue(request)
                .retrieve().bodyToMono(StkResponse.class);
    }

    @Async
    public void depositTask(String userId, DepositDto dto) {
        try {
            String token = mpesaLogin.login();
            log.info("login completed");
            StkRequest request = StkRequest.builder()
                    .accountReference("Test")
                    .amount(dto.getAmount().toString())
                    .businessShortCode(bsc)
                    .callBackURL(callBack)
                    .partyA(dto.getPhoneNo())
                    .partyB(bsc)
                    .password(pass)
                    .phoneNumber(dto.getPhoneNo())
                    .timestamp("20230629110689")
                    .transactionDesc("Test")
                    .transactionType("CustomerPayBillOnline")
                    .build();
            StkResponse response = depositRequester(request, token).block();
            assert response != null;
            log.info(response.toString());
            if (!response.getResponseCode().equals("0"))
                throw new RuntimeException("error occurred during request deposit");
            log.info("request completed");
            Payment payment = Payment.builder().paymentStatus(PaymentStatus.PENDING).paymentType(PaymentType.CREDIT).amount(dto.getAmount()).userId(userId).checkoutId(response.getCheckoutRequestID()).build();
            paymentRepository.save(payment);
        } catch (RuntimeException exception) {
            log.error(exception.getMessage(), exception);
        }
    }

    @Async
    public void verifyDeposit(MpesaStkCallback request) {
        String resultCode = request.getEnvelope().getBody().getStkCallback().getResultCode();
        try {
            int parsedResultCode = Integer.parseInt(resultCode);
            if (parsedResultCode == 0) {
                Payment payment = getPayment(request.getEnvelope().getBody().getStkCallback().getCheckoutRequestID());
                payment.setPaymentStatus(PaymentStatus.SUCCESS);
                payment = paymentRepository.save(payment);
                String[] userId = payment.getUserId().split("_");
                BalanceResponseDto dto = couponManagementService.depositBalanceForDriver(BalanceRequestDto.builder().amount(BigDecimal.valueOf(payment.getAmount())).userId(Integer.valueOf(userId[1])).build());
                log.info("verify deposit response {}", dto.toString());
            } else
                throw new RuntimeException("");
        } catch (RuntimeException e) {
            Payment payment = getPayment(request.getEnvelope().getBody().getStkCallback().getCheckoutRequestID());
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            log.error("verify deposit response {}", e.getMessage());
        }

    }

    private Payment getPayment(String checkoutId) {
        List<Payment> payments = paymentRepository.findAll(PaymentSpecification.withPaymentStatusAndTypeAndCheckoutId(PaymentStatus.PENDING, PaymentType.CREDIT, checkoutId));
        if (payments.isEmpty())
            throw new RuntimeException("id not found");
        return payments.get(0);
    }
}
