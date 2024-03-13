package et.com.gebeya.parkinglotservice.dto.requestdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Result {
    private Integer resultType;
    private Integer resultCode;
    private String resultDesc;
    private String originatorConversationID;
    private String conversationID;
    private String transactionID;
    private ResultParameters resultParameters;
}
