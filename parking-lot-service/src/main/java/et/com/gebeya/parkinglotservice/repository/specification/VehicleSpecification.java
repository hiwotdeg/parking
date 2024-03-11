package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.model.Vehicle;
import jakarta.persistence.criteria.Join;
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

    public static Specification<Vehicle> getVehicleByDriverId(Integer driverId) {
        return (root, query, criteriaBuilder) -> {
            Join<Vehicle, Driver> driverJoin = root.join("driver");
            Predicate isActive = criteriaBuilder.isTrue(root.get("isActive"));
            Predicate isDriver = criteriaBuilder.equal(driverJoin.get("id"), driverId);
            return criteriaBuilder.and(isActive, isDriver);
        };
    }
}
