package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateVehicleDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.VehicleRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.VehicleResponseDto;
import et.com.gebeya.parkinglotservice.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleService vehicleService;
    @PostMapping("/vehicles") // driver
    public ResponseEntity<VehicleResponseDto> addVehicle(@Valid @RequestBody VehicleRequestDto vehicleRequestDto){
        return ResponseEntity.ok(vehicleService.addVehicle(vehicleRequestDto));
    }

    @PatchMapping("/vehicles/{id}") // driver
    public ResponseEntity<VehicleResponseDto> updateVehicle(@RequestBody UpdateVehicleDto updateVehicleDto, @PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.updateVehicle(updateVehicleDto,id));
    }

    @GetMapping("/vehicles/my") // driver
    public ResponseEntity<List<VehicleResponseDto>> getVehiclesByDriverId(){
        return ResponseEntity.ok(vehicleService.getVehiclesByDriverId());
    }

    @GetMapping("/vehicles/{id}") // driver, provider
    public ResponseEntity<VehicleResponseDto> getVehicleByVehicleId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.getVehiclesByVehicleId(id));
    }

    @DeleteMapping("/vehicles/{id}") // driver
    public ResponseEntity<Map<String, String>> deleteVehicleById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(vehicleService.deleteVehicleById(id));
    }


}
