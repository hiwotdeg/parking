package et.com.gebeya.parkinglotservice.dto.requestdto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateProviderRequestDto {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String imageUrl;
}
