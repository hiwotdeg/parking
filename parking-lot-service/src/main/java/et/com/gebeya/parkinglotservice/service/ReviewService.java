package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import et.com.gebeya.parkinglotservice.util.MappingUtil;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DriverRepository driverRepository;
    private final ParkingLotRepository parkingLotRepository;

    public Review createReviewForParkingLot(AddReviewRequestDto reviewRequest){
        Driver driver = getDriverById(reviewRequest.getDriverId());
        ParkingLot parkingLot = getParkingLotById(reviewRequest.getParkingLotId());
        Review review = MappingUtil.mapAddReviewRequestDtoToReview(reviewRequest);
        review.setDriverId(driver);
        review.setParkingLot(parkingLot);
        return reviewRepository.save(review);
    }

    public Review updateReviewForParkingLot(UpdateReviewRequestDto updateReview){
        Driver driver = getDriverById(updateReview.getDriverId());
        ParkingLot parkingLot = getParkingLotById(updateReview.getParkingLotId());
        Review review = MappingUtil.mapUpdateRequestDtoToReview(updateReview);
        review.setDriverId(driver);
        review.setParkingLot(parkingLot);
        return reviewRepository.save(review);
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
