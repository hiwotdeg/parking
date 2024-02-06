package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotProviderRepository extends JpaRepository<ParkingLotProvider,Integer> {
}
