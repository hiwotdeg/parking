package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.DriverResponseDto;
import et.com.gebeya.parkinglotservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/drivers")
    public ResponseEntity<AddUserResponse> registerDriver(@RequestBody AddDriverRequestDto dto){
        return ResponseEntity.ok(driverService.registerDriver(dto));
    }
    @PatchMapping("/drivers/{id}")
    public ResponseEntity<DriverResponseDto> updateDriver(@RequestBody UpdateDriverRequestDto dto, @PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.updateDriver(dto,id));
    }
    @GetMapping("/drivers/{id}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }
    @GetMapping("/drivers/")
    public ResponseEntity<DriverResponseDto> getDriverById(){
        return ResponseEntity.ok(driverService.getDriverById());
    }

}
