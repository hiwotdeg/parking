package et.com.gebeya.notificationservice.listener;

import et.com.gebeya.notificationservice.dto.Otpdto;
import et.com.gebeya.notificationservice.service.SMSService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private SMSService smsService;


    @KafkaListener(topics = "OTP", groupId = "group1",
            containerFactory = "messagedtoListenerFactory"
    )
    void dtoListener(Otpdto dto) {
        smsService.sendOtp(dto);
    }
}


