package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateReviewRequestDto {
    private Integer driverId;
    private Integer parkingLotId;
    private Float rate;
    private String comment;
}
