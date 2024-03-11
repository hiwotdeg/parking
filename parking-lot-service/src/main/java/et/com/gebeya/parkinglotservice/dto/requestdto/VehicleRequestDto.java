package et.com.gebeya.parkinglotservice.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VehicleRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String model;
    private Integer year;
    @NotBlank
    private String plate;
}
