package et.com.gebeya.parkinglotservice.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReservationResponseDto {
    private Integer id;
    private Integer driverId;
    private Integer parkingLotId;
    private LocalTime stayingDuration;
    private BigDecimal price;
    private Boolean isReservationAccepted;
    private Instant reservedAt;
}