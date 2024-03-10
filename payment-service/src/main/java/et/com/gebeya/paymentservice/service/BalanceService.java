package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.request.TransferBalanceDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    BalanceResponseDto createBalance(BalanceDto balanceDto) {
        Balance balance = MappingUtil.mapBalanceRequestDtoToBalance(balanceDto);
        balance.setAmount(BigDecimal.valueOf(0.0));
        balance = balanceRepository.save(balance);
        return MappingUtil.mapBalanceToBalanceResponseDto(balance);
    }
    BalanceResponseDto withdrawalBalance(BalanceDto balanceDto) {
       Balance provider = getUser(balanceDto.getUserId());
        if (balanceDto.getBalance().compareTo(BigDecimal.valueOf(100)) < 0)
            throw new InSufficientAmount("The minimum withdrawal amount is 100");
        if (provider.getAmount().compareTo(balanceDto.getBalance()) < 0)
            throw new InSufficientAmount("Your Balance is inSufficient. please purchase coupon");
        provider.setAmount(provider.getAmount().subtract(balanceDto.getBalance()));
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(provider));
    }
    BalanceResponseDto depositBalance(BalanceDto balanceDto) {
        Balance driver = getUser(balanceDto.getUserId());
       driver.setAmount(driver.getAmount().add(balanceDto.getBalance()));
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(driver));
    }
    BalanceResponseDto transferBalance(TransferBalanceDto transferBalanceDto) {
       Balance driver = getUser(transferBalanceDto.getDriverId());
       Balance provider = getUser(transferBalanceDto.getProviderId());
        BigDecimal updatedDriverBalance = driver.getAmount().subtract(transferBalanceDto.getAmount());
        if (updatedDriverBalance.compareTo(BigDecimal.ZERO) < 0)
            throw new InSufficientAmount("Your Balance is inSufficient. please purchase coupon");
        driver.setAmount(updatedDriverBalance);
        BigDecimal updatedProviderBalance = provider.getAmount().add(transferBalanceDto.getAmount());
        provider.setAmount(updatedProviderBalance);
        balanceRepository.save(provider);
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(driver));

    }
    BalanceResponseDto checkBalance(String id){
        Balance balance = getUser(id);
        return MappingUtil.mapBalanceToBalanceResponseDto(balance);
    }
    private Balance getUser(String id){
        List<Balance> user = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(id));
        if(user.isEmpty())
            throw new AccountBlocked("Your Account is blocked. please contact the admins");
        return user.get(0);
    }

    Map<String,String> deleteUser(String id){
        Balance user = getUser(id);
        balanceRepository.delete(user);
        return Map.of("message","users Coupon account deleted successfully");
    }

}
