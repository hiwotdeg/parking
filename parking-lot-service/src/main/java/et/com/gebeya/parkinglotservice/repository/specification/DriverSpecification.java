package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Driver;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class DriverSpecification {
    public static Specification<Driver> getDriverById(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isDriver = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isDriver);
        };
    }

}
