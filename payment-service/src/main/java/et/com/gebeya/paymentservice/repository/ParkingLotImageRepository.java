package et.com.gebeya.paymentservice.repository;


import et.com.gebeya.paymentservice.model.ParkingLotImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotImageRepository extends JpaRepository<ParkingLotImage,Integer> {
}
