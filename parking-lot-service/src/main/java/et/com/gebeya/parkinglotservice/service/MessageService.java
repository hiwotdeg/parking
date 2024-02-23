package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.MessageDto;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static et.com.gebeya.parkinglotservice.util.Constant.PUSH_NOTIFICATION;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final ParkingLotRepository parkingLotRepository;
    private final KafkaTemplate<String,MessageDto> messageDtoKafkaTemplate;
    public String sendMessage(Integer id, String body){
        List<ParkingLot> parkingLot = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if(!parkingLot.isEmpty())
        {
            Integer providerId = parkingLot.get(0).getParkingLotProvider().getId();
            String receiverId = "PROVIDER_"+providerId;
            MessageDto messageDto = MessageDto.builder().title("test").type("push_notification").body(body).receiverId(receiverId).build();
            messageDtoKafkaTemplate.send(PUSH_NOTIFICATION,messageDto);
            return "message sent successfully";
        }
        return "parkingLot can not be found";
    }
}
