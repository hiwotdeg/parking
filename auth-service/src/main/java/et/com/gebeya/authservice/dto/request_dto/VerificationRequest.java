package et.com.gebeya.authservice.dto.request_dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class VerificationRequest {
    @NotBlank
    @Pattern(regexp = "^\\+251[79]\\d{8}$", message = "Phone Number starts with +251*********")
    private String phoneNo;
    @NotBlank
    @Size(min = 5, max = 6)
    private String otp;
}
