package et.com.gebeya.notificationservice.listener;

import et.com.gebeya.notificationservice.dto.MessageDto;
import et.com.gebeya.notificationservice.dto.Otpdto;
import et.com.gebeya.notificationservice.service.MessageService;
import et.com.gebeya.notificationservice.service.SMSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static et.com.gebeya.notificationservice.util.Constant.AUTH_TOPIC;
import static et.com.gebeya.notificationservice.util.Constant.NOTIFICATION_TOPIC;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaListeners {

    private final SMSService smsService;
    private final MessageService messageService;


    @KafkaListener(topics = AUTH_TOPIC, groupId = "group1", containerFactory = "messagedtoListenerFactory")
    void dtoListener(Otpdto dto) {
        log.info(dto.toString());
        smsService.sendOtp(dto);
    }

    @KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "group5", containerFactory = " ")
    void messageListener(MessageDto dto)
    {
        log.info(dto.toString());
        messageService.sendPushNotification(dto);

    }
}


