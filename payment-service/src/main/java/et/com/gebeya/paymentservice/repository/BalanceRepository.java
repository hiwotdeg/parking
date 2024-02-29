package et.com.gebeya.paymentservice.repository;

import et.com.gebeya.paymentservice.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BalanceRepository extends JpaRepository<Balance,Integer> {
}
