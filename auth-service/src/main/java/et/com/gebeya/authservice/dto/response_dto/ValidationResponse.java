package et.com.gebeya.authservice.dto.response_dto;


import et.com.gebeya.authservice.enums.Authority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidationResponse {
        private final Authority role;
        private final Integer roleId;
}
