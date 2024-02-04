package et.com.gebeya.authservice.dto.response_dto;



import et.com.gebeya.authservice.enums.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Authority authority;
}
