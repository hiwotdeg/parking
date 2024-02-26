package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Vehicle;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class VehicleSpecification {
    public static Specification<Vehicle> getVehicleById(Integer id){
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isParkingLot = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isParkingLot);
        };
    }
}
