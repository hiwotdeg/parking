package et.com.gebeya.authservice.dto.request_dto;

import et.com.gebeya.authservice.enums.Authority;
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
