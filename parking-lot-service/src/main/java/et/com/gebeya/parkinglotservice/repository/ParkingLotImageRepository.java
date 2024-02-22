package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.ParkingLotImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotImageRepository extends JpaRepository<ParkingLotImage,Integer> {
}
