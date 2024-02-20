package et.com.gebeya.parkinglotservice.model;

import et.com.gebeya.parkinglotservice.enums.ParkingType;
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
@Table(name = "parking_lot")
@Builder
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
    private Integer capacity;
    private String imageUrl;
    private Integer availableSlot;
    private ParkingType parkingType;
    private Float rating;
    private Boolean isActive;
    @OneToOne()
    private ParkingLotProvider provider;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant updatedOn;
}
