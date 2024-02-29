package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.TransferBalanceDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.exception.AccountBlocked;
import et.com.gebeya.paymentservice.exception.InSufficientAmount;
import et.com.gebeya.paymentservice.model.Balance;
import et.com.gebeya.paymentservice.repository.BalanceRepository;
import et.com.gebeya.paymentservice.repository.specification.BalanceSpecification;
import et.com.gebeya.paymentservice.util.MappingUtil;
import jakarta.transaction.Transactional;
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
        balance.setAmount(BigDecimal.valueOf(0.0));
        balance = balanceRepository.save(balance);
        return MappingUtil.mapBalanceToBalanceResponseDto(balance);
    }

    BalanceResponseDto withdrawalBalance(BalanceDto balanceDto) {
        List<Balance> list = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(balanceDto.getUserId()));
        if (list.isEmpty())
            throw new AccountBlocked("Your Account is blocked. please contact the admins");
        else if (balanceDto.getBalance().compareTo(BigDecimal.valueOf(100)) < 0)
            throw new InSufficientAmount("The minimum withdrawal amount is 100");
        else if (list.get(0).getAmount().compareTo(balanceDto.getBalance()) < 0)
            throw new InSufficientAmount("Your Balance is inSufficient. please purchase coupon");
        list.get(0).setAmount(list.get(0).getAmount().subtract(balanceDto.getBalance()));
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(list.get(0)));

    }

    BalanceResponseDto depositBalance(BalanceDto balanceDto){
        List<Balance> list = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(balanceDto.getUserId()));
        if (list.isEmpty())
            throw new AccountBlocked("Your Account is blocked. please contact the admins");
        list.get(0).setAmount(list.get(0).getAmount().add(balanceDto.getBalance()));
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(list.get(0)));
    }

    BalanceResponseDto transferBalance(TransferBalanceDto transferBalanceDto){
        List<Balance> driver = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(transferBalanceDto.getDriverId()));
        if (driver.isEmpty())
            throw new AccountBlocked("Your Account is blocked. please contact the admins");
        List<Balance> provider = balanceRepository.findAll(BalanceSpecification.getBalanceByUserId(transferBalanceDto.getProviderId()));
        if (provider.isEmpty())
            throw new AccountBlocked("Your Account is blocked. please contact the admins");
        BigDecimal updatedDriverBalance = driver.get(0).getAmount().subtract(transferBalanceDto.getAmount());
        if (updatedDriverBalance.compareTo(BigDecimal.ZERO)<0)
            throw new InSufficientAmount("Your Balance is inSufficient. please purchase coupon");
        driver.get(0).setAmount(updatedDriverBalance);
        BigDecimal updatedProviderBalance = provider.get(0).getAmount().add(transferBalanceDto.getAmount());
        provider.get(0).setAmount(updatedProviderBalance);
        balanceRepository.save(provider.get(0));
        return MappingUtil.mapBalanceToBalanceResponseDto(balanceRepository.save(driver.get(0)));

    }


}
