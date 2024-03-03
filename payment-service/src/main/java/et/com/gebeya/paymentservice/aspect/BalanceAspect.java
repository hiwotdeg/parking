package et.com.gebeya.paymentservice.aspect;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.TransferBalanceRequestDto;
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

    @AfterReturning(pointcut = "execution(* et.com.gebeya.paymentservice.service.CouponManagementService.withdrawalBalanceForProvider(..))",returning = "result")
    public void afterCouponManagementServiceWithdrawalBalanceForProvider(JoinPoint joinPoint, Object result){
        Object[] args = joinPoint.getArgs();
        BalanceRequestDto balanceRequestDto = (BalanceRequestDto) args[0];
        log.info("afterCouponManagementServiceWithdrawBalanceForProvider aspect is working");
        messagingService.sendWithdrawalMessageForProvider(balanceRequestDto.getUserId(), balanceRequestDto.getAmount());
    }

    @AfterReturning(pointcut = "execution(* et.com.gebeya.paymentservice.service.CouponManagementService.transferBalance(..))",returning = "result")
    public void afterTransferBalance(JoinPoint joinPoint, Object result){
        log.info("afterTransferBalance aspect is working");
        Object[] args = joinPoint.getArgs();
        TransferBalanceRequestDto transferBalanceRequestDto = (TransferBalanceRequestDto) args[0];
        messagingService.sendTransferMessageForCouponFromDriverToProvider(transferBalanceRequestDto.getDriverId(),transferBalanceRequestDto.getProviderId(),transferBalanceRequestDto.getAmount());
    }
}
