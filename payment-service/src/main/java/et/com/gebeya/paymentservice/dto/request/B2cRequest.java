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
public class B2cRequest {
    @JsonProperty("InitiatorName")
    private String initiatorName;
    @JsonProperty("SecurityCredential")
    private String securityCredential;
    @JsonProperty("Occassion")
    private String occassion;
    @JsonProperty("CommandID")
    private String commandID;
    @JsonProperty("PartyA")
    private String partyA;
    @JsonProperty("PartyB")
    private String partyB;
    @JsonProperty("Remarks")
    private String remarks;
    @JsonProperty("Amount")
    private Integer amount;
    @JsonProperty("QueueTimeOutURL")
    private String queueTimeOutURL;
    @JsonProperty("ResultURL")
    private String resultURL;


}
