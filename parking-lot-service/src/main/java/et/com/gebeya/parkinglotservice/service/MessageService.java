package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.MessageDto;
import et.com.gebeya.parkinglotservice.enums.PushNotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static et.com.gebeya.parkinglotservice.util.Constant.PUSH_NOTIFICATION;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {
    private final KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate;

    public void sendPushNotificationForProvider(Integer id, String body) {
        String providerId = "PROVIDER_" + id;
        MessageDto messageDto = MessageDto.builder().title("BOOK NOTIFICATION").type(PushNotificationType.RESERVATION.name()).body(body).receiverId(providerId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }

    public void sendPushNotificationForDriver(Integer id, String body) {
        String providerId = "DRIVER_" + id;
        MessageDto messageDto = MessageDto.builder().title("BOOK NOTIFICATION").type(PushNotificationType.RESERVATION.name()).body(body).receiverId(providerId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }


}
