package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddParkingLotDto {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private ParkingType parkingType;
    private String imageUrl;
}
