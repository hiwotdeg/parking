package et.com.gebeya.geolocationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ParkingLotDto {
    private Integer id;
    private String title;
    private String address;
}
