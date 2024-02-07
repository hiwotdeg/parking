package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.AddUserResponse;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.service.ParkingLotProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final ParkingLotProviderService parkingLotProviderService;

    @PostMapping("/register")
    public ResponseEntity<AddUserResponse> registerParkingLotProvider(@RequestBody ParkingLotProvider parkingLotProvider){
        return ResponseEntity.ok(parkingLotProviderService.registerParkingLotProvider(parkingLotProvider));
    }
}
