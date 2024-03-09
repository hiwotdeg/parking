package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.DriverResponseDto;
import et.com.gebeya.parkinglotservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/drivers") //unsecured endpoint
    public ResponseEntity<AddUserResponse> registerDriver(@RequestBody AddDriverRequestDto dto){
        return ResponseEntity.ok(driverService.registerDriver(dto));
    }
    @PatchMapping("/drivers/{id}") //drivers only
    public ResponseEntity<DriverResponseDto> updateDriver(@RequestBody UpdateDriverRequestDto dto, @PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.updateDriver(dto,id));
    }
    @GetMapping("/drivers/{id}") //drivers,providers
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }
    @GetMapping("/drivers/my") //drivers only
    public ResponseEntity<DriverResponseDto> getDriverById(){
        return ResponseEntity.ok(driverService.getMyDriverProfile());
    }
    @GetMapping("/drivers") //admin
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return ResponseEntity.ok(driverService.getAllDrivers(pageable));
    }

}
