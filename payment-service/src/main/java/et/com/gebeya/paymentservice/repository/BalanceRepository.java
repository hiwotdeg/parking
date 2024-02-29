package et.com.gebeya.paymentservice.repository;

import et.com.gebeya.paymentservice.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Integer>, JpaSpecificationExecutor<Balance> {
}
