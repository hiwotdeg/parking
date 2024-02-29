package et.com.gebeya.paymentservice.repository;


import et.com.gebeya.paymentservice.model.ParkingLotProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotProviderRepository extends JpaRepository<ParkingLotProvider,Integer>, JpaSpecificationExecutor<ParkingLotProvider> {
}
