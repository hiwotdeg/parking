package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateAdminRequestDto {
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNo;
    private String email;
}
