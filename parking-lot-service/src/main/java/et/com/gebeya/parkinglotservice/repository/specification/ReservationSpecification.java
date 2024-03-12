package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.model.Reservation;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {
    private ReservationSpecification() {
    }

    public static Specification<Reservation> getReservationByProviderId(Integer providerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Join<ParkingLot, ParkingLotProvider> providerJoin = parkingLotJoin.join("parkingLotProvider");
            return criteriaBuilder.equal(providerJoin.get("id"), providerId);
        };
    }

    public static Specification<Reservation> getReservationById(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isReservation = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isReservation);
        };
    }
}
