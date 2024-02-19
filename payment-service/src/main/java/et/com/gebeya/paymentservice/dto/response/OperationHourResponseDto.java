package et.com.gebeya.paymentservice.dto.response;

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
public class OperationHourResponseDto {
    private LocalTime startTime;
    private LocalTime endTime;
    private BigDecimal price;
}
