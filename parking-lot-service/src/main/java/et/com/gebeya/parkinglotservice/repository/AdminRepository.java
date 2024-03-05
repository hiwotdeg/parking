package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
}
