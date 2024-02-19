package et.com.gebeya.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AddOperationRequestDto {
    private Integer parkingLotId;
    private List<OperationHourDto> operationHour;
}
