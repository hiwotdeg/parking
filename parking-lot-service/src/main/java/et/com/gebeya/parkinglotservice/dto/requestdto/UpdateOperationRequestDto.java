package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateOperationRequestDto {
    private Integer parkingLotId;
    private LocalTime startTime;
    private LocalTime endTIme;
    private BigDecimal price;
}
