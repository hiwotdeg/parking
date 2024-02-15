package et.com.gebeya.parkinglotservice.dto.requestdto;

import et.com.gebeya.parkinglotservice.enums.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddUserRequest {
    private String phoneNo;
    private Authority role;
    private Integer roleId;
}
