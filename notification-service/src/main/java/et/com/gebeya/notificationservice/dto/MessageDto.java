package et.com.gebeya.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MessageDto {
    private String title;
    private String body;
    private String type;
    private String receiverId;
}
