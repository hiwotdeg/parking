package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.exception.MultipleReviewException;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.exception.ReviewIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.ReviewRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ReviewSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import et.com.gebeya.parkinglotservice.util.MappingUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DriverRepository driverRepository;
    private final ParkingLotRepository parkingLotRepository;

    public ReviewResponseDto createReviewForParkingLot(AddReviewRequestDto reviewRequest, Integer parkingLotId){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Review> reviews = reviewRepository.findAll(ReviewSpecification.getByParkingLotAndDriver(parkingLotId,driverId));
        if(!reviews.isEmpty())
            throw new MultipleReviewException("a driver can give you only one review per parkingLot");
        Driver driver = getDriverById(driverId);
        ParkingLot parkingLot = getParkingLotById(parkingLotId);
        Review review = MappingUtil.mapAddReviewRequestDtoToReview(reviewRequest);
        review.setDriverId(driver);
        review.setParkingLot(parkingLot);
        reviewRepository.save(review);
        return MappingUtil.reviewResponse(review);
    }

    public ReviewResponseDto updateReviewForParkingLot(UpdateReviewRequestDto updateReview, Integer parkingLotId, Integer reviewId){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Review> review = reviewRepository.findAll(ReviewSpecification.getByParkingLotReviewAndDriver(parkingLotId,driverId,reviewId));
        if(review.isEmpty())
            throw new ReviewIdNotFound("review id not found");
        Review reviewList = reviewRepository.save(MappingUtil.mapUpdateRequestDtoToReview(review.get(0), updateReview));
        return MappingUtil.reviewResponse(reviewList);
    }

    public void updateOverallRatingForParkingLot(Integer parkingLotId){
        ParkingLot parkingLot = getParkingLotById(parkingLotId);
        Float averageRating = reviewRepository.calculateAverageRatingByParkingLotId(parkingLotId);
        parkingLot.setRating(averageRating);
        parkingLotRepository.save(parkingLot);
    }

    public Map<String,String> deleteReview(Integer parkingLotId, Integer reviewId){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Review> review = reviewRepository.findAll(ReviewSpecification.getByParkingLotReviewAndDriver(parkingLotId,driverId,reviewId));
        if(review.isEmpty())
            throw new ReviewIdNotFound("review id not found");
        review.get(0).setIsActive(false);
        reviewRepository.save(review.get(0));
        Map<String, String> response = new HashMap<>();
        response.put("message", "review deleted successfully");
        return response;
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

//    private Review getReviewsByParkingLotIdAndDriverId(Integer parkingLotID, Integer driverId){
//        List<Review> reviews = reviewRepository.findAll(ReviewSpecification.getByParkingLotAndDriver(parkingLotID,driverId));
//
//    }



}
