package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateReservation {
    @NotBlank
    private ReservationStatus reservationStatus;
}
