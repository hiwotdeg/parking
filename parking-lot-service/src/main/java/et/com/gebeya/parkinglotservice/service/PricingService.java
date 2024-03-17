package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.PriceRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.PricingResponseDto;
import et.com.gebeya.parkinglotservice.model.OperationHour;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final OperationHourService operationHourService;


    public PricingResponseDto dynamicPricing(PriceRequestDto request, Integer parkingLotId) {
        List<OperationHour> operationHours = operationHourService.getOperationByParkingLotId(parkingLotId);
        LocalTime initialTime = LocalTime.now();
        LocalTime finalTime = LocalTime.now().plusHours(request.getDuration().getHour()).plusMinutes(request.getDuration().getMinute());
        BigDecimal totalPrice = BigDecimal.ZERO;
        if (initialTime.isAfter(finalTime)) {
            LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, initialTime.getHour(), initialTime.getMinute());
            LocalDateTime endTime = LocalDateTime.of(2024, 1, 2, finalTime.getHour(), finalTime.getMinute());
            totalPrice = getPrice(operationHours, totalPrice, startTime, endTime);
        } else {
            LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, initialTime.getHour(), initialTime.getMinute());
            LocalDateTime endTime = LocalDateTime.of(2024, 1, 1, finalTime.getHour(), finalTime.getMinute());
            totalPrice = getPrice(operationHours, totalPrice, startTime, endTime);
        }
        return PricingResponseDto.builder().amount(totalPrice).build();
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

