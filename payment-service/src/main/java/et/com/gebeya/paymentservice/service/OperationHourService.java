package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.paymentservice.model.OperationHour;
import et.com.gebeya.paymentservice.model.ParkingLot;
import et.com.gebeya.paymentservice.repository.OperationHourRepository;
import et.com.gebeya.paymentservice.repository.ParkingLotRepository;
import et.com.gebeya.paymentservice.repository.specification.OperationHourSpecification;
import et.com.gebeya.paymentservice.repository.specification.ParkingLotSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationHourService {
    private final OperationHourRepository operationHourRepository;
    private final ParkingLotRepository parkingLotRepository;


    public List<OperationHour> getOperationHoursById(Integer id) {
        return operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(id));
    }


    private ParkingLot getParkingLotBy(Integer id) {
        List<ParkingLot> parkingLotList = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if (parkingLotList.isEmpty())
            throw new ParkingLotIdNotFound("ParkingLot id not found");
        return parkingLotList.get(0);
    }

    public BigDecimal calculateDynamicPrice(Integer parkingLotId, LocalTime endTime1) {
        List<OperationHour> operationHours = getOperationHoursById(parkingLotId);
        LocalTime startTime = timeConverter(LocalTime.of(3, 15));
        BigDecimal totalPrice = BigDecimal.ZERO;
        LocalTime endTime = timeConverter(endTime1);

        for (OperationHour operationHour : operationHours) {
            LocalTime operationStartTime = timeConverter(operationHour.getStartTime());
            LocalTime operationEndTime = timeConverter(operationHour.getEndTime());
            BigDecimal pricePerHour = BigDecimal.valueOf(operationHour.getPricePerHour());

            if (operationStartTime.isBefore(endTime) && operationEndTime.isAfter(startTime)) {
                LocalTime overlapStartTime = operationStartTime.isAfter(startTime) ? operationStartTime : startTime;
                LocalTime overlapEndTime = operationEndTime.isBefore(endTime) ? operationEndTime : endTime;
                BigDecimal overlapPrice = priceCalculator(overlapStartTime, overlapEndTime, pricePerHour,operationStartTime);
                totalPrice = totalPrice.add(overlapPrice);
            } else if (startTime.isAfter(endTime)) {
                if (operationStartTime.isBefore(startTime) && operationEndTime.isAfter(startTime)) {
                    LocalTime overlapEndTime = LocalTime.of(23, 59);
                    BigDecimal overlapPrice = priceCalculator(startTime, overlapEndTime, pricePerHour,operationStartTime);
                    totalPrice = totalPrice.add(overlapPrice);
                } else {
                    LocalTime overlapStartTime = LocalTime.of(0, 0);
                    LocalTime overlapEndTime = operationEndTime.isBefore(endTime) ? operationEndTime : endTime;
                    BigDecimal overlapPrice = priceCalculator(overlapStartTime, overlapEndTime, pricePerHour, operationStartTime);
                    totalPrice = totalPrice.add(overlapPrice);
                }
            }
        }

        return totalPrice;
    }


    private BigDecimal priceCalculator(LocalTime overlapStartTime, LocalTime overlapEndTime, BigDecimal pricePerHour, LocalTime endtime) {
        long overlapDuration = Math.abs(overlapDuration(overlapStartTime, overlapEndTime, endtime));
        return pricePerHour.multiply(BigDecimal.valueOf(overlapDuration));
    }

    private static LocalTime timeConverter(LocalTime localTime) {
        return localTime.minusHours(6);
    }


    private long overlapDuration(LocalTime startTime, LocalTime endTIme, LocalTime end) {
        long overlapDurationMinutes = Duration.between(startTime, endTIme).toMinutes();

        if(startTime.isAfter(endTIme))
        {


                LocalTime time = LocalTime.of(23, 59); // Replace with your desired time
                Duration duration1 = Duration.between(startTime, time);
                Duration duration2 = Duration.between(end, endTIme);
                long totalHours = duration1.toHours() + duration2.toHours();
                long totalMinutes = (duration1.toMinutes()%60) + (duration2.toMinutes()%60);
                totalHours += totalMinutes / 60;
                totalMinutes %= 60;
                if(totalMinutes>30)
                    return totalHours+1;
                else return totalHours;

        }

        long minute = overlapDurationMinutes % 60;
        if (minute > 30)
            return Duration.between(startTime, endTIme).toHours() + 1;
        else
            return Duration.between(startTime, endTIme).toHours();
    }

}

