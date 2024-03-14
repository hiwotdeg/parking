package et.com.gebeya.paymentservice.repository.specification;

import et.com.gebeya.paymentservice.enums.PaymentStatus;
import et.com.gebeya.paymentservice.enums.PaymentType;
import et.com.gebeya.paymentservice.model.Payment;
import org.springframework.data.jpa.domain.Specification;

public class PaymentSpecification {
    private PaymentSpecification(){}

    public static Specification<Payment> withPaymentStatusAndTypeAndCheckoutId(
            PaymentStatus paymentStatus, PaymentType paymentType, String checkoutId) {
        return Specification
                .where(withPaymentStatus(paymentStatus))
                .and(withPaymentType(paymentType))
                .and(withCheckoutId(checkoutId));
    }

    private static Specification<Payment> withPaymentStatus(PaymentStatus paymentStatus) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paymentStatus"), paymentStatus);
    }

    private static Specification<Payment> withPaymentType(PaymentType paymentType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("paymentType"), paymentType);
    }

    private static Specification<Payment> withCheckoutId(String checkoutId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("checkoutId"), checkoutId);
    }
}