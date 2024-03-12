package et.com.gebeya.parkinglotservice.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReviewSearch {
    private Integer id;
    private String comment;
    private Instant createdAt;
    private Instant updatedAt;
    private Float rate;
    private ReviewDriver driver;
}
