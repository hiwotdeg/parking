package et.com.gebeya.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Item {
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Value")
    private String value;
}
