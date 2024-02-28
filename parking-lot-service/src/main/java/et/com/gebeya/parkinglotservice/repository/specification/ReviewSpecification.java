package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Review;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ReviewSpecification {
    public static Specification<Review> getReviewByParkingLotId(Integer parkingLotId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> providerJoin = root.join("parkingLot");
            Predicate isActive = criteriaBuilder.isTrue(providerJoin.get("isActive"));
            Predicate isParkingLot = criteriaBuilder.equal(providerJoin.get("id"), parkingLotId);
            return criteriaBuilder.and(isActive, isParkingLot);
        };
    }


    public static Specification<Review> getByParkingLotAndDriver(Integer parkingLotId, Integer driverId) {
        return (root, query, criteriaBuilder) -> {
            Join<Review, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Join<Review, Driver> driverJoin = root.join("driverId");

            Predicate parkingLotPredicate = criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
            Predicate driverPredicate = criteriaBuilder.equal(driverJoin.get("id"), driverId);

            return criteriaBuilder.and(parkingLotPredicate, driverPredicate);

        };
    }
}