package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.AddParkingLotRequest;
import et.com.gebeya.parkinglotservice.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot/lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/AddLot")
    public ResponseEntity<?> addParkingLot(AddParkingLotRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(parkingLotService.addParkingLot(request, file));
    }
}
