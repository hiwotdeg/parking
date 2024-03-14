package et.com.gebeya.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReferenceItem {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("Value")
    private String value;
}
