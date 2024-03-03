package et.com.gebeya.paymentservice.aspect;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.service.MessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class BalanceAspect {
    private final MessagingService messagingService;
    @AfterReturning(pointcut = "execution(* et.com.gebeya.paymentservice.service.CouponManagementService.depositBalanceForDriver(..))",returning = "result")
    public void afterCouponManagementServiceDepositBalanceForDriver(JoinPoint joinPoint, Object result){
        Object[] args = joinPoint.getArgs();
        BalanceRequestDto balanceRequestDto = (BalanceRequestDto) args[0];
        log.info("afterCouponManagementServiceDepositBalanceForDriver aspect is working");
        messagingService.sendDepositMessageForDriver(balanceRequestDto.getUserId(), balanceRequestDto.getAmount());
    }
}
