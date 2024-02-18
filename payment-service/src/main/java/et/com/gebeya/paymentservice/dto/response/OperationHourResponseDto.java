package et.com.gebeya.paymentservice.dto.response;

import java.math.BigDecimal;
import java.time.LocalTime;

public class OperationHourResponseDto {
    LocalTime startTime;
    LocalTime endTime;
    BigDecimal price;
}
