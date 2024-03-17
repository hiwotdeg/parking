package et.com.gebeya.paymentservice.service.payment;


import et.com.gebeya.paymentservice.dto.response.MpesaLoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class MpesaNewLogin {
    private final WebClient.Builder webClientBuilder;
    @Value("${mpesa.stk.username}")
    private String username;
    @Value("${mpesa.stk.password}")
    private String password;

    String login() {
        String authorization = encodeCredentials(username, password);
        log.info("basic authorization==>{}", authorization);
        MpesaLoginResponse loginResponse = getToken(authorization).block();
        assert loginResponse != null;
        return "Bearer " + loginResponse.getAccessToken();
    }
    private static String encodeCredentials(String username, String password) {
        String credentials = username + ":" + password;
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        String encodedCredentials = Base64.getEncoder().encodeToString(credentialsBytes);
        return "Basic " + encodedCredentials;
    }

    private Mono<MpesaLoginResponse> getToken(String authorization) {
        return webClientBuilder.build()
                .get()
                .uri("https://apisandbox.safaricom.et/v1/token/generate?grant_type=client_credentials")
                .header("Authorization", authorization)
                .retrieve().bodyToMono(MpesaLoginResponse.class);
    }


}

