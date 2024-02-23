package et.com.gebeya.notificationservice.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationResponseDto {
    private final String role;
    private final Integer roleId;
}
