package et.com.gebeya.parkinglotservice.service;


import et.com.gebeya.parkinglotservice.dto.requestdto.AddOperationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.OperationHourDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.OperationHourResponseDto;
import et.com.gebeya.parkinglotservice.exception.OperationHourIdNotFound;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.model.OperationHour;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.OperationHourRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.specification.OperationHourSpecification;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationHourService {
    private final OperationHourRepository operationHourRepository;
    private final ParkingLotRepository parkingLotRepository;

    private ParkingLot getParkingLot(Integer id) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if (parkingLots.isEmpty()) throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }


    public List<OperationHourResponseDto> addOperationHour(List<OperationHourDto> request, Integer parkingId) {
        ParkingLot parkingLot = getParkingLot(parkingId);
        List<OperationHour> operationHours = MappingUtil.addOperationRequestDtoToOperationHour(parkingLot, request);
        return MappingUtil.listOfOperationHourToListOfOperationHourResponseDto(operationHourRepository.saveAll(operationHours));
    }
    public Map<String, String> deleteOperationHour(Integer parkingId, Integer operationHourId){
        Integer providerId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OperationHour> operationHours = operationHourRepository.findAll(OperationHourSpecification.hasParkingLotOperationAndProviderId(parkingId,operationHourId,providerId));
        if(operationHours.isEmpty())
            throw new OperationHourIdNotFound("operationHourId not found");
        operationHourRepository.delete(operationHours.get(0));
        Map<String, String> response = new HashMap<>();
        response.put("message", "vehicle deleted successfully");
        return response;
    }


    public List<OperationHourResponseDto> getOperationHoursByParkingLotId(Integer id) {
        List<OperationHour> operationHours = operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(id));
        return MappingUtil.listOfOperationHourToListOfOperationHourResponseDto(operationHours);
    }

    public OperationHourResponseDto getOperationHoursByOperationHourId(Integer parkingLotId,Integer operationHourId){
        List<OperationHour> operationHours = operationHourRepository.findAll(OperationHourSpecification.hasParkingLotAndOperationHourId(parkingLotId,operationHourId));
        return MappingUtil.operationHourToOperationHourResponseDto(operationHours.get(0));
    }


}

