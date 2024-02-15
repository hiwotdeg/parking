package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateParkingLotDto;
import et.com.gebeya.parkinglotservice.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/lot")
    public ResponseEntity<ParkingLotResponseDto> addParkingLot(@RequestBody AddParkingLotDto request){
        return ResponseEntity.ok(parkingLotService.addParkingLot(request));
    }

    @PatchMapping ("/lot/{id}")
    public ResponseEntity<ParkingLotResponseDto> updateParkingLot(@RequestBody UpdateParkingLotDto request,@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotService.updateParkingLot(request,id));
    }

    @GetMapping("/lot/{id}")
    public ResponseEntity<ParkingLotResponseDto> getParkingLot(@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotService.getParkingLotById(id));
    }

}
