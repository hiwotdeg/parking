package et.com.gebeya.paymentservice.util;

import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.model.Balance;

import java.math.BigDecimal;

public class MappingUtil {
    private MappingUtil() {
    }
    public static Balance mapBalanceRequestDtoToBalance(BalanceDto dto){
        return Balance.builder().userId(dto.getUserId()).isActive(true).amount(dto.getBalance()).build();
    }

    public static BalanceResponseDto mapBalanceToBalanceResponseDto(Balance balance){
        return BalanceResponseDto.builder().userId(balance.getUserId()).balance(balance.getAmount()).build();
    }



}
