package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateLocationRequestDto {
    private Integer id;
    private String existingTitle;
    private String existingAddress;
    private String newTitle;
    private String newAddress;
    private Double latitude;
    private Double longitude;
}
