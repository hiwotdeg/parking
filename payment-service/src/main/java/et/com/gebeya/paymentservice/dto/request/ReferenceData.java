package et.com.gebeya.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ReferenceData {
    @JsonProperty("ReferenceItem")
    private ReferenceItem referenceItem;
}
