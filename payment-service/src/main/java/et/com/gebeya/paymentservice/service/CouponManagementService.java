package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.CreateBalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.CreateBalanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CouponManagementService {
    private final BalanceService balanceService;

    public CreateBalanceResponse createBalanceForDriver(CreateBalanceRequestDto dto){
        String driverId = "DRIVER"+dto.getUserId();
        BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder().balance(BigDecimal.valueOf(0.0)).userId(driverId).build();
        return balanceService.createBalance(balanceRequestDto);
    }

    public CreateBalanceResponse createBalanceForProvider(CreateBalanceRequestDto dto){
        String driverId = "PROVIDER"+dto.getUserId();
        BalanceRequestDto balanceRequestDto = BalanceRequestDto.builder().balance(BigDecimal.valueOf(0.0)).userId(driverId).build();
        return balanceService.createBalance(balanceRequestDto);
    }


}
