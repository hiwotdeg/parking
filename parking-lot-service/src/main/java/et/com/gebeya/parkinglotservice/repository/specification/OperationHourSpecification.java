package et.com.gebeya.parkinglotservice.repository.specification;


import et.com.gebeya.parkinglotservice.model.OperationHour;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class OperationHourSpecification {
    public static Specification<OperationHour> hasParkingLotId(int parkingLotId) {
        return (root, query, criteriaBuilder) -> {
            Join<OperationHour, ParkingLot> parkingLotJoin = root.join("parkingLot", JoinType.INNER);
            return criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
        };
    }
}
