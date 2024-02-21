package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;


public interface ParkingLotRepository extends JpaRepository<ParkingLot,Integer>, JpaSpecificationExecutor<ParkingLot> {
}
