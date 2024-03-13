package et.com.gebeya.paymentservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class Result {
    @JsonProperty("ResultType")
    private Integer resultType;
    @JsonProperty("ResultCode")
    private Integer resultCode;
    @JsonProperty("ResultDesc")
    private String resultDesc;
    @JsonProperty("OriginatorConversationID")
    private String originatorConversationID;
    @JsonProperty("ConversationID")
    private String conversationID;
    @JsonProperty("TransactionID")
    private String transactionID;
    @JsonProperty("ResultParameters")
    private ResultParameters resultParameters;
    @JsonProperty("ReferenceData")
    private ReferenceData referenceData;
}
