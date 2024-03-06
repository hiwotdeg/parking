package et.com.gebeya.paymentservice.listeners;

import et.com.gebeya.paymentservice.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final MessagingService messagingService;
    @KafkaListener(topics = ADD_LOCATION_TOPIC, groupId = "group2", containerFactory = "addLocationListenerFactory")
    void addLocationDtoListener(AddLocationDto dto) {
        System.out.println(dto);
        locationService.addLocation(dto);
    }
}
