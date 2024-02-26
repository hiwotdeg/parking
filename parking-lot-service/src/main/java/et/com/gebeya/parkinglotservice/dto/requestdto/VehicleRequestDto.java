package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VehicleRequestDto {
    private String name;
    private String model;
    private Integer year;
    private String plate;
}
