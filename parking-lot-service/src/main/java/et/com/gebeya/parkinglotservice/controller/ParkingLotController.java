package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ResponseModel;
import et.com.gebeya.parkinglotservice.service.ParkingLotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class ParkingLotController {
    private final ParkingLotService parkingLotService;

    @PostMapping("/lots") // providers
    public ResponseEntity<ParkingLotResponseDto> addParkingLot(@Valid @RequestBody AddParkingLotDto request) {
        return ResponseEntity.ok(parkingLotService.addParkingLot(request));
    }

    @PatchMapping("/lots/{id}") // providers
    public ResponseEntity<ParkingLotResponseDto> updateParkingLot(@RequestBody UpdateParkingLotDto request, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(parkingLotService.updateParkingLot(request, id));
    }

    @GetMapping("/lots/{id}") // providers, drivers
    public ResponseEntity<ParkingLotResponseDto> getParkingLot(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(parkingLotService.getParkingLotById(id));
    }

    @GetMapping("/lots/my") // providers
    public ResponseEntity<ParkingLotResponseDto> getMyParkingLot() {
        return ResponseEntity.ok(parkingLotService.getMyParkingLotByProviderId());
    }

    @GetMapping("/lots") // admin
    public ResponseEntity<List<ParkingLotResponseDto>> getAllParkingLot(@PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(parkingLotService.getAllParkingLots(pageable));
    }

    @DeleteMapping("/lots/{id}") // providers
    public ResponseEntity<ResponseModel> deleteParkingLot(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(parkingLotService.deleteParkingLot(id));
    }

}
