package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.DriverResponseDto;
import et.com.gebeya.parkinglotservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @PostMapping("/drivers")
    @CrossOrigin
    public ResponseEntity<AddUserResponse> registerDriver(@RequestBody AddDriverRequestDto dto){
        return ResponseEntity.ok(driverService.registerDriver(dto));
    }
    @PatchMapping("/drivers/{id}")
    @CrossOrigin
    public ResponseEntity<DriverResponseDto> updateDriver(@RequestBody UpdateDriverRequestDto dto,@PathVariable("id") Integer id){
        return ResponseEntity.ok(driverService.updateDriver(dto,id));
    }
    @GetMapping("/drivers/{id}")
    @CrossOrigin
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable Integer id){
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

//    @GetMapping("/driver/{id}")
//    public ResponseEntity<String> test(@PathVariable Integer id){
//        return ResponseEntity.ok("test 1 2 3 " + id);
//    }
}
