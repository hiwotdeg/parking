package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.AddOperationRequestDto;
import et.com.gebeya.paymentservice.dto.request.PriceRequestDto;
import et.com.gebeya.paymentservice.dto.response.OperationHourResponseDto;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<OperationHourResponseDto> addOperationHour(AddOperationRequestDto request) {
        ParkingLot parkingLot = getParkingLot(request.getParkingLotId());
        List<OperationHour> operationHours = MappingUtil.addOperationRequestDtoToOperationHour(parkingLot, request.getOperationHour());
        return MappingUtil.listOfOperationHourToListOfOperationHourResponseDto(operationHourRepository.saveAll(operationHours));
    }


    public List<OperationHour> getOperationHoursById(Integer id) {
        return operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(id));

    }


    public BigDecimal dynamicPricing(PriceRequestDto request) {
        List<OperationHour> operationHours = getOperationHoursById(request.getParkingLotId());
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (request.getStartTime().isAfter(request.getEndTime())) {
            LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, request.getStartTime().getHour(), request.getStartTime().getMinute());
            LocalDateTime endTime = LocalDateTime.of(2024, 1, 2, request.getEndTime().getHour(), request.getEndTime().getMinute());
            totalPrice = getPrice(operationHours, totalPrice, startTime, endTime);
        } else {
            LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, request.getStartTime().getHour(), request.getStartTime().getMinute());
            LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, request.getEndTime().getHour(), request.getEndTime().getMinute());
            totalPrice = getPrice(operationHours, totalPrice, startTime, endTime);
        }
        return totalPrice;
    }

    private BigDecimal getPrice(List<OperationHour> operationHours, BigDecimal totalPrice, LocalDateTime startTime, LocalDateTime endTime) {
        for (OperationHour operationHour : operationHours) {
            LocalDateTime operationStartTime = operationHour.getStartTime();
            LocalDateTime operationEndTime = operationHour.getEndTime();
            BigDecimal pricePerHour = operationHour.getPricePerHour();
            if (operationStartTime.isBefore(endTime) && operationEndTime.isAfter(startTime)) {
                LocalDateTime overlapStartTime = operationStartTime.isAfter(startTime) ? operationStartTime : startTime;
                LocalDateTime overlapEndTime = operationEndTime.isBefore(endTime) ? operationEndTime : endTime;
                BigDecimal overlapPrice = priceCalculator(overlapStartTime, overlapEndTime, pricePerHour);
                totalPrice = totalPrice.add(overlapPrice);

            }

        }
        return totalPrice;
    }


    private BigDecimal priceCalculator(LocalDateTime overlapStartTime, LocalDateTime overlapEndTime, BigDecimal pricePerHour) {
        long overlapDuration = Math.abs(durationCalculator(overlapStartTime, overlapEndTime));
        return pricePerHour.multiply(BigDecimal.valueOf(overlapDuration));
    }


    private long durationCalculator(LocalDateTime startTime, LocalDateTime endTime) {
        long overlapDurationMinutes = Duration.between(startTime, endTime).toMinutes();
        long minute = overlapDurationMinutes % 60;
        if (minute > 30) return Duration.between(startTime, endTime).toHours() + 1;
        else return Duration.between(startTime, endTime).toHours();
    }


}

