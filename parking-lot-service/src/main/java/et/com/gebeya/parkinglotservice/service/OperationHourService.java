package et.com.gebeya.parkinglotservice.service;


import et.com.gebeya.parkinglotservice.dto.requestdto.OperationHourDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UserDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.OperationHourResponseDto;
import et.com.gebeya.parkinglotservice.exception.OperationHourIdNotFound;
import et.com.gebeya.parkinglotservice.model.OperationHour;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.repository.OperationHourRepository;
import et.com.gebeya.parkinglotservice.repository.specification.OperationHourSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OperationHourService {
    private final OperationHourRepository operationHourRepository;
    private final ParkingLotService parkingLotService;
    public List<OperationHourResponseDto> addOperationHour(List<OperationHourDto> request, Integer parkingId) {
        ParkingLot parkingLot = parkingLotService.getParkingLot(parkingId);
        List<OperationHour> operationHours = MappingUtil.addOperationRequestDtoToOperationHour(parkingLot, request);
        return MappingUtil.listOfOperationHourToListOfOperationHourResponseDto(operationHourRepository.saveAll(operationHours));
    }
    public Map<String, String> deleteOperationHour(Integer parkingId, Integer operationHourId){
        UserDto providerId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<OperationHour> operationHours = operationHourRepository.findAll(OperationHourSpecification.hasParkingLotOperationAndProviderId(parkingId,operationHourId,providerId.getId()));
        if(operationHours.isEmpty())
            throw new OperationHourIdNotFound("operationHourId not found");
        operationHourRepository.delete(operationHours.get(0));
        Map<String, String> response = new HashMap<>();
        response.put("message", "vehicle deleted successfully");
        return response;
    }


    public List<OperationHourResponseDto> getOperationHoursByParkingLotId(Integer id) {
        List<OperationHour> operationHours = getOperationByParkingLotId(id);
        return MappingUtil.listOfOperationHourToListOfOperationHourResponseDto(operationHours);
    }

    public OperationHourResponseDto getOperationHoursByOperationHourId(Integer parkingLotId,Integer operationHourId){
        List<OperationHour> operationHours = operationHourRepository.findAll(OperationHourSpecification.hasParkingLotAndOperationHourId(parkingLotId,operationHourId));
        return MappingUtil.operationHourToOperationHourResponseDto(operationHours.get(0));
    }

   List<OperationHour> getOperationByParkingLotId(Integer id){
        parkingLotService.getParkingLot(id);
        return operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(id));
    }


}

