package et.com.gebeya.geolocationservice.listeners;

import et.com.gebeya.geolocationservice.dto.AddLocationDto;
import et.com.gebeya.geolocationservice.dto.DeleteLocationDto;
import et.com.gebeya.geolocationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static et.com.gebeya.geolocationservice.util.Constant.ADD_LOCATION_TOPIC;
import static et.com.gebeya.geolocationservice.util.Constant.DELETE_LOCATION_TOPIC;

@Component
@RequiredArgsConstructor
public class KafkaListeners {

private final LocationService locationService;


    @KafkaListener(topics = ADD_LOCATION_TOPIC, groupId = "group2", containerFactory = "addLocationListenerFactory")
    void addLocationDtoListener(AddLocationDto dto) {
        System.out.println(dto);
        locationService.addLocation(dto);
    }

    @KafkaListener(topics = DELETE_LOCATION_TOPIC, groupId = "group3", containerFactory = "deleteLocationListenerFactory")
    void deleteLocationDtoListener(DeleteLocationDto dto) {
        System.out.println(dto);
        locationService.deleteLocation(dto);
    }
}


