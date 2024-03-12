package et.com.gebeya.parkinglotservice.dto.requestdto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateReviewRequestDto {
    @Size(min = 0, max = 5)
    private Float rate;
    private String comment;
}
