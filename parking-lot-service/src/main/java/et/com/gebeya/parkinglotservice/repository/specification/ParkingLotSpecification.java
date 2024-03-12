package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ParkingLotSpecification {
    private ParkingLotSpecification() {
    }

    public static Specification<ParkingLot> getParkingLotById(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isParkingLot = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isParkingLot);
        };
    }


    public static Specification<ParkingLot> getParkingLotByProviderId(Integer providerId) {
        return (root, query, criteriaBuilder) -> {
            Join<ParkingLot, ParkingLotProvider> providerJoin = root.join("parkingLotProvider");
            Predicate isActive = criteriaBuilder.isTrue(providerJoin.get("isActive"));
            Predicate isProvider = criteriaBuilder.equal(providerJoin.get("id"), providerId);
            return criteriaBuilder.and(isActive, isProvider);
        };
    }

    public static Specification<ParkingLot> getAllParkingLot() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get("isActive"), false));
    }
}
