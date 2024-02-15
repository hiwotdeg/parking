package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class ParkingLotProviderSpecification {
    public static Specification<ParkingLotProvider> getProviderById(Integer id){
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isProvider = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isProvider);
        };
    }
}
