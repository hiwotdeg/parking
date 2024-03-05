package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.model.Reservation;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class ReservationSpecification {
    public static Specification<Reservation> getReservationByProviderId(Integer providerId) {
        return (root, query, criteriaBuilder) -> {
            Join<Reservation, ParkingLot> parkingLotJoin = root.join("parkingLot");
            Join<ParkingLot, ParkingLotProvider> providerJoin = parkingLotJoin.join("parkingLotProvider");
            return criteriaBuilder.equal(providerJoin.get("id"), providerId);
        };
    }
}
