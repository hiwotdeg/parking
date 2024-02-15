package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Integer>, JpaSpecificationExecutor<Driver> {
}
