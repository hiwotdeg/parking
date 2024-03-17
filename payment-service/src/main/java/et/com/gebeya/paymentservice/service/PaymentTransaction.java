package et.com.gebeya.paymentservice.service.payment;

import et.com.gebeya.paymentservice.dto.request.*;
import et.com.gebeya.paymentservice.dto.response.ResponseModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentTransaction {
    private final PaymentNewService paymentNewService;
    @Transactional
    public void handleResponseForWithdrawal(MpesaB2CResponse response){
        paymentNewService.confirmWithdraw(response);
    }
    @Transactional
    public void handleResponseForDeposit(MpesaStkCallback response){
        paymentNewService.confirmDeposit(response);
    }

    @Transactional
    public ResponseModel requestDeposit(DepositDto dto){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String driverId = userId.getRole() + "_" + userId.getId();
        paymentNewService.initiateDeposit(driverId, dto.getPhoneNo(), dto.getAmount());
        return ResponseModel.builder().message("your deposit request will be processed").build();
    }
    @Transactional
    public ResponseModel requestWithdrawal(WithdrawDto dto){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerId = userId.getRole() + "_" + userId.getId();
        paymentNewService.initiateWithdraw(providerId,dto.getPhoneNo(),dto.getAmount());
        return ResponseModel.builder().message("your withdraw request will be processed").build();
    }

}
