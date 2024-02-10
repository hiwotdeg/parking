package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Integer> {
}
