package et.com.gebeya.parkinglotservice.repository.specification;


import et.com.gebeya.parkinglotservice.model.OperationHour;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.security.Provider;

public class OperationHourSpecification {
    private OperationHourSpecification() {
    }

    public static Specification<OperationHour> hasParkingLotId(int parkingLotId) {
        return (root, query, criteriaBuilder) -> {
            Join<OperationHour, ParkingLot> parkingLotJoin = root.join("parkingLot", JoinType.INNER);
            return criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId);
        };
    }


    public static Specification<OperationHour> hasParkingLotAndOperationHourId(int parkingLotId, int operationHourId) {
        return (root, query, criteriaBuilder) -> {
            Join<OperationHour, ParkingLot> parkingLotJoin = root.join("parkingLot", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId),
                    criteriaBuilder.equal(root.get("id"), operationHourId)
            );
        };
    }


    public static Specification<OperationHour> hasParkingLotOperationAndProviderId(int parkingLotId, int operationHourId, int providerId) {
        return (root, query, criteriaBuilder) -> {
            Join<OperationHour, ParkingLot> parkingLotJoin = root.join("parkingLot", JoinType.INNER);
            Join<ParkingLot, Provider> providerJoin = parkingLotJoin.join("parkingLotProvider", JoinType.INNER);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(parkingLotJoin.get("id"), parkingLotId),
                    criteriaBuilder.equal(root.get("id"), operationHourId),
                    criteriaBuilder.equal(providerJoin.get("id"), providerId)
            );
        };
    }

}
