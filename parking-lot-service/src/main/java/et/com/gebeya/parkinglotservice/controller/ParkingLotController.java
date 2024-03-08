package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateParkingLotDto;
import et.com.gebeya.parkinglotservice.service.ParkingLotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/lots")
    public ResponseEntity<ParkingLotResponseDto> addParkingLot(@RequestBody AddParkingLotDto request){
        return ResponseEntity.ok(parkingLotService.addParkingLot(request));
    }


    @PatchMapping ("/lots/{id}")
    public ResponseEntity<ParkingLotResponseDto> updateParkingLot(@RequestBody UpdateParkingLotDto request,@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotService.updateParkingLot(request,id));
    }

    @GetMapping("/lots/{id}")
    public ResponseEntity<ParkingLotResponseDto> getParkingLot(@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotService.getParkingLotById(id));
    }

    @GetMapping("/lots/")
    public ResponseEntity<ParkingLotResponseDto> getParkingLotByProviderId(){
        return ResponseEntity.ok(parkingLotService.getParkingLotByProviderId());
    }

    @DeleteMapping ("/lots/{id}")
    public ResponseEntity<Map<String, String>> deleteParkingLot(@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotService.deleteParkingLot(id));
    }

}
