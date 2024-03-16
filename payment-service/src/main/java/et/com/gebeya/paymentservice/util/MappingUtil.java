package et.com.gebeya.paymentservice.util;

import et.com.gebeya.paymentservice.dto.request.B2cRequest;
import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.request.StkRequest;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.model.Balance;

public class MappingUtil {
    private MappingUtil() {
    }
    public static Balance mapBalanceRequestDtoToBalance(BalanceDto dto){
        return Balance.builder().userId(dto.getUserId()).isActive(true).amount(dto.getBalance()).build();
    }

    public static BalanceResponseDto mapBalanceToBalanceResponseDto(Balance balance){
        return BalanceResponseDto.builder().userId(balance.getUserId()).balance(balance.getAmount()).build();
    }

    public static StkRequest createStkRequest(String amount, String phoneNo, String bsc, String callBackUrl, String pass, String timeStamp, String transactionType){
        return StkRequest.builder()
                .accountReference("Test")
                .amount(amount)
                .businessShortCode(bsc)
                .callBackURL(callBackUrl)
                .partyA(phoneNo)
                .partyB(bsc)
                .password(pass)
                .phoneNumber(phoneNo)
                .timestamp(timeStamp)
                .transactionDesc("Test")
                .transactionType(transactionType)
                .build();
    }

    public static B2cRequest createB2cRequest(String amount,String initiator,String sender,String phoneNo,String remarks,String resultUrl,String securityCredential){
        return  B2cRequest.builder()
                .amount(Integer.valueOf(amount))
                .commandID("BusinessPayment")
                .initiatorName(initiator)
                .occassion("Parking")
                .partyA(sender)
                .partyB(phoneNo)
                .queueTimeOutURL("https://www.myservice:8080/b2c/result")
                .remarks(remarks)
                .resultURL(resultUrl)
                .securityCredential(securityCredential).build();
    }

}
