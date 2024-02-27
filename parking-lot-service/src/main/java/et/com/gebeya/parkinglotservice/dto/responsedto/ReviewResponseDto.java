package et.com.gebeya.parkinglotservice.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewResponseDto {
    private Integer driverId;
    private Integer parkingLotId;
    private Float rate;
    private String comment;
}
