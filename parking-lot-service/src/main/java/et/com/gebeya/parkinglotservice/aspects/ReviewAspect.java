package et.com.gebeya.parkinglotservice.aspects;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ReviewAspect {
    private final ReviewService reviewService;


    @AfterReturning(pointcut = "execution(* et.com.gebeya.parkinglotservice.service.ReviewService.createReviewForParkingLot(..)) || execution(* et.com.gebeya.parkinglotservice.service.ReviewService.updateReviewForParkingLot(..))", returning = "result")
    public void afterReviewServiceUpdateAndCreate(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        Integer parkingLotId =  (Integer) args[1];
        reviewService.updateOverallRatingForParkingLot(parkingLotId);
        log.info("the parking lot Id is {}",parkingLotId);
    }
}
