package et.com.gebeya.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MessageDto {
    private String title;
    private String body;
    private String type;
    private String receiverId;
}
