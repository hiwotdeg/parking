package et.com.gebeya.parkinglotservice.aspects;

import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import lombok.RequiredArgsConstructor;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ReviewAspect {
    private ParkingLotRepository parkingLotRepository;
    private ParkingLotProviderRepository parkingLotProviderRepository;

    @After("execution(* et.com.gebeya.parkinglotservice.service.ReviewService.createReviewForParkingLot(..)) || execution(* et.com.gebeya.parkinglotservice.service.ReviewService.updateReviewForParkingLot(..))")
    public void afterReviewServiceUpdateAndCreate(Joinpoint joinpoint){

    }
}
