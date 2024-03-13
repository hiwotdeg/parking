package et.com.gebeya.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ResultParameter {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("Value")
    private String value;
}
