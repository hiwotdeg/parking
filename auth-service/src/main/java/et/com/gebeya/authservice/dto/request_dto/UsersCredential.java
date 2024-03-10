package et.com.gebeya.authservice.dto.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersCredential {
  @NotBlank
  @Pattern(regexp = "^\\+251[79]\\d{8}$", message = "Phone Number starts with +251*********")
  private String phoneNo;

}
