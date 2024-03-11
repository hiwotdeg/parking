package et.com.gebeya.parkinglotservice.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
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
public class OperationHourDto {
    private LocalTime startTime;
    private LocalTime endTIme;
    private BigDecimal price;
}
