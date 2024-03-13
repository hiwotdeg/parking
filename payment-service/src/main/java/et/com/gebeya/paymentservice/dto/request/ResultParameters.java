package et.com.gebeya.paymentservice.dto.request;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ResultParameters {
    @JsonProperty("ResultParameter")
    private List<ResultParameter> resultParameter;
}
