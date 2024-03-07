package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateReservation {
    private ReservationStatus reservationStatus;
}
