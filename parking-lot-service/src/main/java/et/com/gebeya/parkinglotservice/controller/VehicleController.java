package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.VehicleRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.VehicleResponseDto;
import et.com.gebeya.parkinglotservice.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/vehicles")
    public ResponseEntity<VehicleResponseDto> addVehicle(@RequestBody VehicleRequestDto vehicleRequestDto){
        return ResponseEntity.ok(vehicleService.addVehicle(vehicleRequestDto));
    }

    @PatchMapping("/vehicles/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(@RequestBody VehicleRequestDto vehicleRequestDto,@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleRequestDto,id));
    }


}
