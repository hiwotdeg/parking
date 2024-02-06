package et.com.gebeya.parkinglotservice.model;

import et.com.gebeya.parkinglotservice.enums.Authority;
import et.com.gebeya.parkinglotservice.enums.ParkingLotRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "parking_lot_provider")
public class ParkingLotProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNo;
    private Boolean isActive;
    private Boolean isVerified;
    private Authority role;
//    private ParkingLotRole role;
    private String imageUrl;

}
