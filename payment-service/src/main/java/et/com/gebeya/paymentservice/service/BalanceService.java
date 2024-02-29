package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.CreateBalanceResponse;
import et.com.gebeya.paymentservice.model.Balance;
import et.com.gebeya.paymentservice.repository.BalanceRepository;
import et.com.gebeya.paymentservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;

    CreateBalanceResponse createBalance(BalanceRequestDto balanceRequestDto){
        Balance balance =  MappingUtil.mapBalanceRequestDtoToBalance(balanceRequestDto);
        balance = balanceRepository.save(balance);
        return MappingUtil.mapBalanceToCreateBalanceResponseDto(balance);
    }



}
