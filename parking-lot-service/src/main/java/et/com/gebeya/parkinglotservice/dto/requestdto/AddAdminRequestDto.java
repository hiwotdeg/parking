package et.com.gebeya.parkinglotservice.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddAdminRequestDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String middleName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Pattern(regexp = "^\\+251[79]\\d{8}$", message = "Phone Number starts with +251*********")
    private String phoneNo;
    @NotBlank
    @Email
    private String email;
}
