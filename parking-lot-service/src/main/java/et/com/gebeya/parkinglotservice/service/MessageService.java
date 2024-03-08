package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.requestdto.BalanceRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.MessageDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import et.com.gebeya.parkinglotservice.enums.PushNotificationType;
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
    private final KafkaTemplate<String,MessageDto> messageDtoKafkaTemplate;
    public void sendPushNotificationForProvider(Integer id, String body){
            String providerId = "PROVIDER_"+id;
            MessageDto messageDto = MessageDto.builder().title("BOOK NOTIFICATION").type(PushNotificationType.RESERVATION.name()).body(body).receiverId(providerId).build();
            messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,messageDto);
    }

    public void sendPushNotificationForDriver(Integer id, String body){
        String providerId = "DRIVER_"+id;
        MessageDto messageDto = MessageDto.builder().title("BOOK NOTIFICATION").type(PushNotificationType.RESERVATION.name()).body(body).receiverId(providerId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,messageDto);
    }





}
