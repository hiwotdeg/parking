package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.MessageDto;
import et.com.gebeya.paymentservice.enums.PushNotificationType;
import et.com.gebeya.paymentservice.util.IdConvertorUtil;
import et.com.gebeya.paymentservice.util.MessagingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static et.com.gebeya.paymentservice.util.Constant.PUSH_NOTIFICATION;


@Service
@RequiredArgsConstructor
public class MessagingService {
    private final KafkaTemplate<String, MessageDto> messageDtoKafkaTemplate;

    public void sendDepositMessageForDriver(Integer id, BigDecimal amount) {
        String receiverId = IdConvertorUtil.driverConvertor(id);
        MessageDto messageDto = MessageDto.builder().title("DEPOSIT NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.driverDepositNotification(amount)).receiverId(receiverId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }

    public void sendWithdrawalMessageForProvider(Integer id, BigDecimal amount) {
        String receiverId = IdConvertorUtil.providerConvertor(id);
        MessageDto messageDto = MessageDto.builder().title("WITHDRAWAL NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.providerWithdrawalNotification(amount)).receiverId(receiverId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }

}