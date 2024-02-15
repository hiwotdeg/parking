package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateParkingLotDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private String imageUrl;
    private Integer availableSlot;
    private ParkingType parkingType;
}
