package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessageDto {
    private String message;
}
