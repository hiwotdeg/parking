package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.ReviewSearchRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateReviewRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ReviewSearch;
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
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import et.com.gebeya.parkinglotservice.util.MappingUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final DriverService driverService;
    private final ParkingLotService parkingLotService;
    public ReviewResponseDto createReviewForParkingLot(AddReviewRequestDto reviewRequest, Integer parkingLotId){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Review> reviews = reviewRepository.findAll(ReviewSpecification.getReviewByParkingLotAndDriver(parkingLotId,driverId));
        if(!reviews.isEmpty())
            throw new MultipleReviewException("a driver can give you only one review per parkingLot");
        Driver driver = driverService.getDriver(driverId);
        ParkingLot parkingLot = parkingLotService.getParkingLot(parkingLotId);
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

    public List<ReviewSearch> getAllReviews(Pageable pageable){
        List<Review> reviews = reviewRepository.findAll(ReviewSpecification.getAllReviews(),pageable).stream().toList();
        return MappingUtil.listOfReviewToListOfReviewSearch(reviews);
    }


    public List<ReviewSearch> getReviews(ReviewSearchRequestDto reviewSearchRequestDto, Integer parkingLotId){
        List<Review> reviewList;
        if(reviewSearchRequestDto.getReviewId()!=null && reviewSearchRequestDto.getDriverId()!=null)
            reviewList = reviewRepository.findAll(ReviewSpecification.getByParkingLotReviewAndDriver(parkingLotId, reviewSearchRequestDto.getDriverId(), reviewSearchRequestDto.getReviewId()));
        else if(reviewSearchRequestDto.getReviewId()!=null)
            reviewList = reviewRepository.findAll(ReviewSpecification.getReviewByParkingLotIdAndReviewId(parkingLotId, reviewSearchRequestDto.getReviewId()));
        else if(reviewSearchRequestDto.getDriverId()!=null)
            reviewList = reviewRepository.findAll(ReviewSpecification.getReviewByParkingLotAndDriver(parkingLotId, reviewSearchRequestDto.getDriverId()));
        else
            reviewList = reviewRepository.findAll(ReviewSpecification.getReviewByParkingLotId(parkingLotId));
        return MappingUtil.listOfReviewToListOfReviewSearch(reviewList);
    }



}
