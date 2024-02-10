package et.com.gebeya.parkinglotservice.dto;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddParkingLotRequest {
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private ParkingType parkingType;
}
