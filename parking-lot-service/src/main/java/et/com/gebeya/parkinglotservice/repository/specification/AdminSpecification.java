package et.com.gebeya.parkinglotservice.repository.specification;

import et.com.gebeya.parkinglotservice.model.Admin;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class AdminSpecification {
    private AdminSpecification(){}
    public static Specification<Admin> getAdminById(Integer id) {
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isAdmin = criteriaBuilder.equal(root.get("id"), id);
            return criteriaBuilder.and(isActive, isAdmin);
        };
    }
}
