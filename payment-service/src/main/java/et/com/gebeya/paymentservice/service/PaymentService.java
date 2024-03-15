package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final MpesaC2B mpesaC2B;
    private final MpesaB2C mpesaB2C;
    private final CouponManagementService couponManagementService;

    public Map<String, String> requestDeposit(DepositDto dto) {
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = userId.getRole() + "_" + userId.getId();
        mpesaC2B.depositTask(driverId, dto);
        return Map.of("message", "your deposit request will be processed");
    }

    @Transactional
    public void stkListener(MpesaStkCallback request) {
        mpesaC2B.verifyDeposit(request);
    }

    @Transactional
    public Map<String,String> requestWithdraw(WithdrawDto dto){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerId = userId.getRole() + "_" + userId.getId();
        couponManagementService.withdrawalBalanceCheckerForProvider(dto.getAmount());
        mpesaB2C.withdrawalTask(providerId,dto);
        return Map.of("message", "your withdrawal request will be processed");
    }
    @Transactional
    public void b2cListener(MpesaB2CResponse request) {
        mpesaB2C.verifyWithdraw(request);
    }


}
