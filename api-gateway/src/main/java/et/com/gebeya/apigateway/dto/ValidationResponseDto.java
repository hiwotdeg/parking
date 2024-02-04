package et.com.gebeya.apigateway.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ValidationResponseDto {
    private final Authority role;
    private final Integer roleId;
}
