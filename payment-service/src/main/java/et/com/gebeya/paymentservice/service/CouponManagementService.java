package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.*;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.util.IdConvertorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponManagementService {
    private final BalanceService balanceService;

    public BalanceResponseDto createBalanceForDriver(BalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(driverId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto createBalanceForProvider(BalanceRequestDto dto){
        String providerId = IdConvertorUtil.providerConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(providerId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto withdrawalBalanceForProvider(WithdrawalRequestDto dto){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerId = userId.getRole()+"_"+userId.getId();
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(providerId).build();
        return balanceService.withdrawalBalance(balanceDto);
    }

    public BalanceResponseDto depositBalanceForDriver(BalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(driverId).build();
        return balanceService.depositBalance(balanceDto);
    }

    public BalanceResponseDto transferBalance(TransferBalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getDriverId());
        String providerId = IdConvertorUtil.providerConvertor(dto.getProviderId());
        TransferBalanceDto transferBalanceDto = TransferBalanceDto.builder().driverId(driverId).providerId(providerId).amount(dto.getAmount()).build();
        return balanceService.transferBalance(transferBalanceDto);
    }

    public BalanceResponseDto checkBalance(){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = userId.getRole()+"_"+userId.getId();
        return balanceService.checkBalance(id);
    }

}
