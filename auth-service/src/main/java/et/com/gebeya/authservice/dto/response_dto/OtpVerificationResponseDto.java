package et.com.gebeya.authservice.dto.response_dto;

import et.com.gebeya.authservice.enums.Code;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OtpVerificationResponseDto {
    private String token;
    private Code code;
}
