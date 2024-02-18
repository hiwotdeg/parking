package et.com.gebeya.paymentservice.util;

import et.com.gebeya.paymentservice.dto.request.AddOperationRequestDto;
import et.com.gebeya.paymentservice.dto.request.OperationHourDto;
import et.com.gebeya.paymentservice.dto.response.OperationHourResponseDto;
import et.com.gebeya.paymentservice.model.OperationHour;
import et.com.gebeya.paymentservice.model.ParkingLot;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class MappingUtil {
    private MappingUtil(){}
    private static OperationHour operationHourDtoDtoToOperationHour(ParkingLot parkingLot, OperationHourDto dto){
        if(dto.getStartTime().getHour()>dto.getEndTIme().getHour()){
            return OperationHour.builder().pricePerHour(dto.getPrice())
                    .startTime(LocalDateTime.of(2024,1,1,dto.getStartTime().getHour(),dto.getStartTime().getMinute()))
                    .endTime(LocalDateTime.of(2024,1,2,dto.getEndTIme().getHour(),dto.getEndTIme().getMinute()))
                    .pricePerHour(dto.getPrice())
                    .parkingLot(parkingLot)
                    .build();
        }
        else{
            return OperationHour.builder().pricePerHour(dto.getPrice())
                    .startTime(LocalDateTime.of(2024,1,1,dto.getStartTime().getHour(),dto.getStartTime().getMinute()))
                    .endTime(LocalDateTime.of(2024,1,1,dto.getEndTIme().getHour(),dto.getEndTIme().getMinute()))
                    .pricePerHour(dto.getPrice())
                    .parkingLot(parkingLot)
                    .build();
        }

    }

    public  static List<OperationHour> addOperationRequestDtoToOperationHour(ParkingLot parkingLot, List<OperationHourDto> dto){
        List<OperationHour> operationHours = new ArrayList<>();
        dto.forEach(request-> operationHours.add(operationHourDtoDtoToOperationHour(parkingLot,request)));
        return operationHours;
    }

    public static List<OperationHourResponseDto> listOfOperationHourToListOfOperationHourResponseDto(List<OperationHour> operationHour){
        List<OperationHourResponseDto> operationHourList = new ArrayList<>();
        operationHour.forEach(request->operationHourList.add(operationHourToOperationHourResponseDto(request)));
        return operationHourList;
    }

    private static OperationHourResponseDto operationHourToOperationHourResponseDto(OperationHour operationHour){
        return OperationHourResponseDto.builder()
                .price(operationHour.getPricePerHour())
                .startTime(LocalTime.of(operationHour.getStartTime().getHour(),operationHour.getStartTime().getMinute()))
                .endTime(LocalTime.of(operationHour.getEndTime().getHour(),operationHour.getEndTime().getMinute()))
                .build();
    }
}
