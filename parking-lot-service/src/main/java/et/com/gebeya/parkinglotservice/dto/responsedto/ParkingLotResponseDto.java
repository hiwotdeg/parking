package et.com.gebeya.parkinglotservice.dto.responsedto;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingLotResponseDto {
    private Integer id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private List<String> images;
    private Integer availableSlot;
    private ParkingType parkingType;
    private Float rating;
}
