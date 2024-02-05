package et.com.gebeya.notificationservice.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static et.com.gebeya.notificationservice.util.Constant.AUTH_TOPIC;
@Component
@RequiredArgsConstructor
public class KafkaListener {
//    private final EmailService emailService;


    @org.springframework.kafka.annotation.KafkaListener(topics = "OTP", groupId = "group1",
            containerFactory = "messagedtoListenerFactory"
    )
    void dtoListener(Otp_dto dto) {
        System.out.println(dto.getOtp());
    }
    }

}
