package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.BalanceRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final WebClient.Builder webClientBuilder;
    BalanceResponseDto createBalanceForProvider(BalanceRequestDto balanceRequestDto) {
        return webClientBuilder.build().post()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/provider")
                .bodyValue(balanceRequestDto)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class)
                .block();
    }

    void deleteBalanceForProvider(Integer id) {
        webClientBuilder.build().delete()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/provider/"+id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }

    BalanceResponseDto createBalanceForDriver(BalanceRequestDto balanceRequestDto){
        return webClientBuilder.build().post()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/driver")
                .bodyValue(balanceRequestDto)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class)
                .block();

    }

    void deleteBalanceForDriver(Integer id) {
        webClientBuilder.build().delete()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/driver/"+id)
                .retrieve()
                .bodyToMono(Object.class)
                .block();
    }
}
