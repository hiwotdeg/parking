package et.com.gebeya.paymentservice.repository.specification;

import et.com.gebeya.paymentservice.model.Balance;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class BalanceSpecification {
    private BalanceSpecification(){
    }
    public static Specification<Balance> getBalanceByUserId(String userId) {
        return (root, query, criteriaBuilder) -> {
            Predicate isActive = criteriaBuilder.notEqual(root.get("isActive"), false);
            Predicate isUser = criteriaBuilder.equal(root.get("userId"), userId);
            return criteriaBuilder.and(isActive, isUser);
        };
    }
}
