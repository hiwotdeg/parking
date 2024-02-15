package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateParkingLotDto;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;

    public ParkingLotResponseDto addParkingLot(AddParkingLotDto dto) {
        ParkingLot parkingLot = MappingUtil.mapAddParkingLotToParkingLot(dto);
        parkingLot.setRating(5.0f);
        parkingLot.setAvailableSlot(parkingLot.getCapacity());
        parkingLot = parkingLotRepository.save(parkingLot);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    public ParkingLotResponseDto updateParkingLot(UpdateParkingLotDto dto, Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        parkingLot = parkingLotRepository.save(MappingUtil.updateParkingLot(parkingLot, dto));
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    public ParkingLotResponseDto getParkingLotById(Integer id){
        ParkingLot parkingLot = getParkingLot(id);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    private ParkingLot getParkingLot(Integer id){
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if(parkingLots.isEmpty())
            throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }

}
