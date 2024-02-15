package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateDriverRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private String imageUrl;
}
