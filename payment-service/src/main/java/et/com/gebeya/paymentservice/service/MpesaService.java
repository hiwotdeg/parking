package et.com.gebeya.paymentservice.service;


import et.com.gebeya.paymentservice.dto.request.B2cRequest;
import et.com.gebeya.paymentservice.dto.request.MpesaB2CResponse;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.request.StkRequest;
import et.com.gebeya.paymentservice.dto.response.B2cResponse;
import et.com.gebeya.paymentservice.dto.response.StkResponse;
import et.com.gebeya.paymentservice.dto.response.VerifyResponse;
import et.com.gebeya.paymentservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpesaService implements PaymentGateway{
    @Value("${mpesa.stk.bsc}")
    private String bsc;
    @Value("${mpesa.stk.pass}")
    private String pass;
    @Value("${mpesa.stk.callBack}")
    private String callBack;
    @Value("${mpesa.b2c.initiator}")
    private String initiator;
    @Value("${mpesa.b2c.securityCredential}")
    private String securityCredential;
    @Value("${mpesa.b2c.sender}")
    private String sender;
    @Value("${mpesa.b2c.resultUrl}")
    private String resultUrl;
    private final WebClient.Builder webClientBuilder;
    private final MpesaLogin mpesaLogin;

    @Override
    public String initiateDeposit(String phoneNo, double amount) {
        String token = mpesaLogin.login();
        StkRequest request = MappingUtil.createStkRequest(String.valueOf(amount), phoneNo, bsc, callBack, pass, "20230629110689","CustomerPayBillOnline");
        StkResponse response = depositRequester(request, token).block();
        assert response != null;
        log.info(response.toString());
        if (response.getResponseCode().equals("0"))
            return response.getCheckoutRequestID();
        return null;
    }

    @Override
    public VerifyResponse confirmDeposit(Object response) {
        MpesaStkCallback confirmationResponse = (MpesaStkCallback) response;
        String resultCode = confirmationResponse.getEnvelope().getBody().getStkCallback().getResultCode();
        try {
            int parsedResultCode = Integer.parseInt(resultCode);
            if(parsedResultCode==0)
                return VerifyResponse.builder()
                        .checkoutId(confirmationResponse.getEnvelope().getBody().getStkCallback().getCheckoutRequestID())
                        .isSuccessful(true).build();
            else
                return VerifyResponse.builder()
                        .checkoutId(confirmationResponse.getEnvelope().getBody().getStkCallback().getCheckoutRequestID())
                        .isSuccessful(false).build();

        } catch (Exception e) {
            return VerifyResponse.builder().isSuccessful(false).checkoutId(null).build();
        }
    }

    @Override
    public String initiateWithdrawal(String phoneNo, Integer amount) {
        String token = mpesaLogin.login();
        B2cRequest request = MappingUtil.createB2cRequest(amount,initiator,sender,phoneNo,"test",resultUrl,securityCredential);
        B2cResponse response = withdrawalRequester(request, token).block();
        assert response != null;
        log.info(response.toString());
        if (!response.getResponseCode().equals("0"))
            return null;
        return response.getOriginatorConversationID();
    }

    @Override
    public VerifyResponse confirmWithdrawal(Object response) {
        MpesaB2CResponse confirmationResponse = (MpesaB2CResponse) response;
        try{
            if (confirmationResponse.getResult().getResultCode() == 0)
                return VerifyResponse.builder()
                        .checkoutId(confirmationResponse.getResult().getOriginatorConversationID())
                        .isSuccessful(true).build();
            else
                return VerifyResponse.builder()
                        .checkoutId(confirmationResponse.getResult().getOriginatorConversationID())
                        .isSuccessful(false).build();
        }catch (Exception e){
            return VerifyResponse.builder().isSuccessful(false).checkoutId(null).build();
        }
    }

    private Mono<StkResponse> depositRequester(StkRequest request, String authorization) {
        return webClientBuilder.build()
                .post()
                .uri("https://apisandbox.safaricom.et/mpesa/stkpush/v1/processrequest")
                .header("Authorization", authorization)
                .bodyValue(request)
                .retrieve().bodyToMono(StkResponse.class);
    }

    private Mono<B2cResponse> withdrawalRequester(B2cRequest request, String authorization) {
        return webClientBuilder.build()
                .post()
                .uri("https://apisandbox.safaricom.et/mpesa/b2c/v1/paymentrequest")
                .header("Authorization", authorization)
                .bodyValue(request)
                .retrieve().bodyToMono(B2cResponse.class);
    }
}
