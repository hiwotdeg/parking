package et.com.gebeya.paymentservice.repository;

import et.com.gebeya.paymentservice.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot,Integer>, JpaSpecificationExecutor<ParkingLot> {
}
