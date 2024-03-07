package et.com.gebeya.parkinglotservice.model;

import et.com.gebeya.parkinglotservice.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name ="driver_id")
    private Driver driver;
    @ManyToOne()
    @JoinColumn(name ="parking_lot_id")
    private ParkingLot parkingLot;
    private LocalTime stayingDuration;
    private BigDecimal price;
    private Boolean isActive;
    private ReservationStatus reservationStatus;
    @CreationTimestamp
    private Instant createdOn;
    @UpdateTimestamp
    private Instant updatedOn;
}
