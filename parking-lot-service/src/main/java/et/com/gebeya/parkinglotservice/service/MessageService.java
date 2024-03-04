package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.requestdto.BalanceRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.MessageDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static et.com.gebeya.parkinglotservice.util.Constant.PUSH_NOTIFICATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final WebClient.Builder webClientBuilder;
    private final ParkingLotRepository parkingLotRepository;
    private final KafkaTemplate<String,MessageDto> messageDtoKafkaTemplate;
    public String sendMessage(Integer id, String body){
        List<ParkingLot> parkingLot = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if(!parkingLot.isEmpty())
        {
            Integer providerId = parkingLot.get(0).getParkingLotProvider().getId();
            String receiverId = "PROVIDER_"+providerId;
            MessageDto messageDto = MessageDto.builder().title("test").type("push_notification").body(body).receiverId(receiverId).build();
            messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,messageDto);
            return "message sent successfully";
        }
        return "parkingLot can not be found";
    }
    @Transactional
    public String test(){
        BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder().amount(BigDecimal.valueOf(10000)).userId(1).build();
        BalanceResponseDto tryWithdrawal = withdrawalBalance(balanceRequestDto);
        log.info("test log information{}",tryWithdrawal.getBalance());
        return tryWithdrawal.toString();
    }

    private BalanceResponseDto withdrawalBalance(BalanceRequestDto balanceRequestDto){
        return webClientBuilder.build().post()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/withdrawal")
                .bodyValue(balanceRequestDto)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class)
                .block();

    }


}
