package et.com.gebeya.parkinglotservice.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminResponseDto {
    private Integer id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNo;
    private String email;
}
