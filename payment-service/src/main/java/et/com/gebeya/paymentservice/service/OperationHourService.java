package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.AddOperationRequestDto;
import et.com.gebeya.paymentservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.paymentservice.model.OperationHour;
import et.com.gebeya.paymentservice.model.ParkingLot;
import et.com.gebeya.paymentservice.repository.OperationHourRepository;
import et.com.gebeya.paymentservice.repository.ParkingLotRepository;
import et.com.gebeya.paymentservice.repository.specification.OperationHourSpecification;
import et.com.gebeya.paymentservice.repository.specification.ParkingLotSpecification;
import et.com.gebeya.paymentservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationHourService {
    private final OperationHourRepository operationHourRepository;
    private final ParkingLotRepository parkingLotRepository;

    private ParkingLot getParkingLot(Integer id) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if (parkingLots.isEmpty())
            throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }

    public void AddOperationHour(AddOperationRequestDto request) {
        ParkingLot parkingLot = getParkingLot(request.getParkingLotId());
        List<OperationHour> operationHours = MappingUtil.addOperationRequestDtoToOperationHour(parkingLot, request);
        operationHourRepository.saveAll(operationHours);
    }


    public List<OperationHour> getOperationHoursById(Integer id) {
        return operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(id));

    }


}

