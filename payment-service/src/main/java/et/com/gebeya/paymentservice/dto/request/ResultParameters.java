package et.com.gebeya.paymentservice.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResultParameters {
    @JsonProperty("ResultParameter")
    private List<ResultParameter> resultParameter;
}
