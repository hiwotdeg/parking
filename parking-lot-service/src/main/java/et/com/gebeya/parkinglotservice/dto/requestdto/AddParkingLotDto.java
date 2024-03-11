package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddParkingLotDto {
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private ParkingType parkingType;
    private List<String> imageUrl;
}
