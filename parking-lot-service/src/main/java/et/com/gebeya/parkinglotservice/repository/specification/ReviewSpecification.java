package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {
    public static Specification<Review> getReviewByParkingLotIdAndReviewId(Integer parkingLotId, Integer reviewId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> providerJoin = root.join("parkingLot");
            Predicate isActive = criteriaBuilder.isTrue(providerJoin.get("isActive"));
            Predicate isParkingLot = criteriaBuilder.equal(providerJoin.get("id"), parkingLotId);
            Predicate reviewIdPredicate = criteriaBuilder.equal(root.get("id"), reviewId);
            return criteriaBuilder.and(isActive, isParkingLot);
        };
    }


    public static Specification<Review> getByParkingLotAndDriver(Integer parkingLotId, Integer driverId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Join<Review, Driver> driverJoin = root.join("driverId");

            Predicate parkingLotPredicate = criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
            Predicate driverPredicate = criteriaBuilder.equal(driverJoin.get("id"), driverId);
            Predicate isActivePredicate = criteriaBuilder.isTrue(root.get("isActive"));
            return criteriaBuilder.and(parkingLotPredicate, driverPredicate, isActivePredicate);

        };
    }

    public static Specification<Review> getByParkingLotReviewAndDriver(Integer parkingLotId, Integer driverId, Integer reviewId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Join<Review, Driver> driverJoin = root.join("driverId");
            Predicate parkingLotPredicate = criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
            Predicate driverPredicate = criteriaBuilder.equal(driverJoin.get("id"), driverId);
            Predicate isActivePredicate = criteriaBuilder.isTrue(root.get("isActive"));
            Predicate reviewIdPredicate = criteriaBuilder.equal(root.get("id"), reviewId);

            return criteriaBuilder.and(parkingLotPredicate, driverPredicate, isActivePredicate, reviewIdPredicate);
        };
    }




}