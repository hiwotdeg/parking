package et.com.gebeya.paymentservice.util;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.CreateBalanceResponse;
import et.com.gebeya.paymentservice.model.Balance;

public class MappingUtil {
    private MappingUtil() {
    }
    public static Balance mapBalanceRequestDtoToBalance(BalanceRequestDto dto){
        return Balance.builder().userId(dto.getUserId()).isActive(true).amount(dto.getBalance()).build();
    }

    public static CreateBalanceResponse mapBalanceToCreateBalanceResponseDto(Balance balance){
        return CreateBalanceResponse.builder().userId(balance.getUserId()).balance(balance.getAmount()).build();
    }



}
