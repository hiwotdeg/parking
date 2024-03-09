package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
public class ReviewSpecification {
    private ReviewSpecification(){}

    public static Specification<Review> getReviewByParkingLotIdAndReviewId(Integer parkingLotId, Integer reviewId) {
        return getReviewByParkingLotId(parkingLotId).and(getReviewById(reviewId));
    }

    public static Specification<Review> getReviewByParkingLotAndDriver(Integer parkingLotId, Integer driverId) {
        return getReviewByParkingLotId(parkingLotId).and(getReviewByDriverId(driverId));
    }

    public static Specification<Review> getByParkingLotReviewAndDriver(Integer parkingLotId, Integer driverId, Integer reviewId) {
        return getReviewByParkingLotId(parkingLotId).and(getReviewByDriverId(driverId)).and(getReviewById(reviewId));
    }

    public static Specification<Review> getReviewByParkingLotId(Integer parkingLotId){
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Predicate isActive = criteriaBuilder.isTrue(parkingLotJoin.get("isActive"));
            Predicate isParkingLot = criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
            return criteriaBuilder.and(isActive, isParkingLot);
        };
    }

    public static Specification<Review> getAllReviews(){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("isActive"),false));
    }

    public static Specification<Review> getReviewById(Integer reviewId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), reviewId);
    }

    public static Specification<Review> getReviewByDriverId(Integer driverId){
        return (root, query, criteriaBuilder) -> {
            Join<Review, Driver> driverJoin = root.join("driverId");
            return criteriaBuilder.equal(driverJoin.get("id"), driverId);
        };
    }
}
