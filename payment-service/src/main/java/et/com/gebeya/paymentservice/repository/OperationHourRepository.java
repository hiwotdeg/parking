package et.com.gebeya.paymentservice.repository;

import et.com.gebeya.paymentservice.model.OperationHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationHourRepository extends JpaRepository<OperationHour,Integer>, JpaSpecificationExecutor<OperationHour> {

}
