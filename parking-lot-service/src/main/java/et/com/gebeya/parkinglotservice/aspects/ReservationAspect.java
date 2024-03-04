package et.com.gebeya.parkinglotservice.aspects;

import et.com.gebeya.parkinglotservice.service.ReservationService;
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
public class ReservationAspect {
    private final ReservationService reservationService;
    @AfterReturning(pointcut = "execution(* et.com.gebeya.parkinglotservice.service.ReservationService.book(..))", returning = "result")
    public void afterReviewServiceUpdateAndCreate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        Integer parkingLotId =  (Integer) args[0];
        reservationService.updateAvailableSlotOfParkingLot(parkingLotId);
        log.info("the parking lot Id is {}",parkingLotId);
    }
}
