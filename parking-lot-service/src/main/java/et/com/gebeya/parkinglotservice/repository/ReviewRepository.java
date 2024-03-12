package et.com.gebeya.parkinglotservice.repository;

import et.com.gebeya.parkinglotservice.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>, JpaSpecificationExecutor<Review> {
    @Query("SELECT AVG(r.rate) FROM Review r WHERE r.parkingLot.id = :parkingLotId")
    Float calculateAverageRatingByParkingLotId(@Param("parkingLotId") Integer parkingLotId);
}
