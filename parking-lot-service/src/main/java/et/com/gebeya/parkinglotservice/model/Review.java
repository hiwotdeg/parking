package et.com.gebeya.parkinglotservice.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String comment;
    private Float rate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_lot_id")
    private ParkingLot parkingLot;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driverId;
    private Boolean isActive;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant updatedOn;
}
