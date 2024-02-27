package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import et.com.gebeya.parkinglotservice.util.MappingUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DriverRepository driverRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ReviewResponseDto createReviewForParkingLot(AddReviewRequestDto reviewRequest){
        Driver driver = getDriverById(reviewRequest.getDriverId());
        ParkingLot parkingLot = getParkingLotById(reviewRequest.getParkingLotId());
        Review review = MappingUtil.mapAddReviewRequestDtoToReview(reviewRequest);
        review.setDriverId(driver);
        review.setParkingLot(parkingLot);
        reviewRepository.save(review);
        return MappingUtil.reviewResponse(review);
    }

    public ReviewResponseDto updateReviewForParkingLot(UpdateReviewRequestDto updateReview){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Driver driver = getDriverById(driverId);
        ParkingLot parkingLot = getParkingLotById(updateReview.getParkingLotId());
        Review review = new Review();
        review.setDriverId(driver);
        review.setParkingLot(parkingLot);
        reviewRepository.save(MappingUtil.mapUpdateRequestDtoToReview(review, updateReview));
        return MappingUtil.reviewResponse(review);
    }


    public Driver getDriverById(Integer id){
        Optional<Driver> driverOptional = driverRepository.findById(id);
        if(driverOptional.isPresent()) return driverOptional.get();
        throw new DriverIdNotFound("Driver is not found");
    }

    public ParkingLot getParkingLotById(Integer id){
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository.findById(id);
        if(parkingLotOptional.isPresent()) return parkingLotOptional.get();
        throw new ParkingLotIdNotFound("Parking lot not found");
    }

}
