package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.AddUserResponse;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class ParkingLotController {
    private final RegistrationService registrationService;
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/register/lot-provider")
    public ResponseEntity<AddUserResponse> registerParkingLotProvider(@RequestBody ParkingLotProvider parkingLotProvider){
        return ResponseEntity.ok(registrationService.registerParkingLotProvider(parkingLotProvider));
    }
}
