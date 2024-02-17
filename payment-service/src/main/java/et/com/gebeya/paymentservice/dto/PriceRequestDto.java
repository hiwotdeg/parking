package et.com.gebeya.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PriceRequestDto {
    private Integer parkingLotId;
    private LocalTime endTime;
}
