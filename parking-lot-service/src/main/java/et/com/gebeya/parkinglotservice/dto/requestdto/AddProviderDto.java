package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddProviderDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
}
