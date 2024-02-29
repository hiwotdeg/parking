package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.exception.AccountBlocked;
import et.com.gebeya.paymentservice.exception.InSufficientAmount;
import et.com.gebeya.paymentservice.model.Balance;
import et.com.gebeya.paymentservice.repository.BalanceRepository;
import et.com.gebeya.paymentservice.repository.specification.BalanceSpecification;
import et.com.gebeya.paymentservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    BalanceResponseDto createBalance(BalanceDto balanceDto) {
        Balance balance = MappingUtil.mapBalanceRequestDtoToBalance(balanceDto);
        balance = balanceRepository.save(balance);
        return MappingUtil.mapBalanceToCreateBalanceResponseDto(balance);
    }

    BalanceResponseDto withdrawalBalance(BalanceDto balanceDto) {
        List<Balance> list = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(balanceDto.getUserId()));
        if (list.isEmpty()) throw new AccountBlocked("Your Account is blocked. please contact the admins");
        else if (balanceDto.getBalance().compareTo(BigDecimal.valueOf(100)) < 0)
            throw new InSufficientAmount("The minimum withdrawal amount is 100");
        else if (list.get(0).getAmount().compareTo(balanceDto.getBalance()) < 0)
            throw new InSufficientAmount("Your Balance is inSufficient. please purchase coupon");
        list.get(0).setAmount(list.get(0).getAmount().subtract(balanceDto.getBalance()));
        return MappingUtil.mapBalanceToCreateBalanceResponseDto(balanceRepository.save(list.get(0)));

    }


}
