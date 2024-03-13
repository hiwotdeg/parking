package et.com.gebeya.authservice.dto.request_dto;

import et.com.gebeya.authservice.enums.Authority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddUserRequest {
    @Pattern(regexp = "^\\+251[79]\\d{8}$", message = "Phone Number starts with +251*********")
    @NotBlank
    private String phoneNo;
    private Authority role;
    @NotNull
    private Integer roleId;
}
