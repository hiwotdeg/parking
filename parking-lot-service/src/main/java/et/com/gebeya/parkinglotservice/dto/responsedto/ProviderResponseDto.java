package et.com.gebeya.parkinglotservice.dto.responsedto;

import et.com.gebeya.parkinglotservice.enums.ParkingLotRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponseDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String imageUrl;
    private ParkingLotRole role;
}
