package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.DepositDto;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.dto.request.UserDto;
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


}
