package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceDto;
import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponManagementService {
    private final BalanceService balanceService;

    public BalanceResponseDto createBalanceForDriver(BalanceRequestDto dto){
        String driverId = "DRIVER_"+dto.getUserId();
        BalanceDto balanceDto = BalanceDto.builder().userId(driverId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto createBalanceForProvider(BalanceRequestDto dto){
        String providerId = "PROVIDER_"+dto.getUserId();
        BalanceDto balanceDto = BalanceDto.builder().userId(providerId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto withdrawalBalanceForProvider(BalanceRequestDto dto){
        String providerId = "PROVIDER_"+dto.getUserId();
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(providerId).build();
        return balanceService.withdrawalBalance(balanceDto);
    }

    public BalanceResponseDto depositBalanceForDriver(BalanceRequestDto dto){
        String driverId = "DRIVER_"+dto.getUserId();
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(driverId).build();
        return balanceService.depositBalance(balanceDto);
    }



}
