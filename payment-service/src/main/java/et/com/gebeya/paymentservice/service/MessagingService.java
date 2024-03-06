package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.CreditOrDebitMessageDto;
import et.com.gebeya.paymentservice.dto.request.MessageDto;
import et.com.gebeya.paymentservice.dto.request.TransferMessageDto;
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

    private void sendDepositMessageForDriver(String receiverId, BigDecimal amount) {
        MessageDto messageDto = MessageDto.builder().title("DEPOSIT NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.driverDepositNotification(amount)).receiverId(receiverId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }

    private void sendWithdrawalMessageForProvider(String receiverId, BigDecimal amount) {
        MessageDto messageDto = MessageDto.builder().title("WITHDRAWAL NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.providerWithdrawalNotification(amount)).receiverId(receiverId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION, messageDto);
    }

    public void sendCreditOrDebitMessage(CreditOrDebitMessageDto dto){
        String [] user = dto.getUserId().split("_");
        if(user[0].equals("PROVIDER"))
            sendWithdrawalMessageForProvider(dto.getUserId(), dto.getAmount());
        else if(user[0].equals("DRIVER"))
            sendDepositMessageForDriver(dto.getUserId(), dto.getAmount());
    }

    public void sendTransferMessageForCouponFromDriverToProvider(TransferMessageDto dto){
        String providerId = IdConvertorUtil.providerConvertor(dto.getProviderId());
        String driverId = IdConvertorUtil.driverConvertor(dto.getDriverId());
        MessageDto providerMessage = MessageDto.builder().title("TRANSFER NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.providerTransferNotification(dto.getAmount(),driverId)).receiverId(providerId).build();
        MessageDto driverMessage = MessageDto.builder().title("TRANSFER NOTIFICATION").type(PushNotificationType.PAYMENT.name()).body(MessagingUtil.driverTransferNotification(dto.getAmount(),providerId)).receiverId(driverId).build();
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,providerMessage);
        messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,driverMessage);
    }

}
