package et.com.gebeya.paymentservice.listeners;

import et.com.gebeya.paymentservice.dto.request.CreditOrDebitMessageDto;
import et.com.gebeya.paymentservice.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static et.com.gebeya.paymentservice.util.Constant.CREDIT_OR_DEBIT_MESSAGE;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final MessagingService messagingService;
    @KafkaListener(topics = CREDIT_OR_DEBIT_MESSAGE, groupId = "group5", containerFactory = "creditOrDebitListenerFactory")
    void creditOrDebitDtoListener(CreditOrDebitMessageDto dto) {
        messagingService.sendCreditOrDebitMessage(dto);
    }
}
