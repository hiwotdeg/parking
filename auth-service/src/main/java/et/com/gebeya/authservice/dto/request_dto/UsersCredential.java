package et.com.gebeya.authservice.dto.request_dto;

import et.com.gebeya.authservice.enums.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersCredential {
  private String phoneNo;

}
