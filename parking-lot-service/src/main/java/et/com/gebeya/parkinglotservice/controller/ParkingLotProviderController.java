package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddProviderDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateProviderRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.ProviderResponseDto;
import et.com.gebeya.parkinglotservice.service.ParkingLotProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class ParkingLotProviderController {
    private final ParkingLotProviderService parkingLotProviderService;

    @PostMapping("/providers")
    public ResponseEntity<AddUserResponse> registerParkingLotProvider(@RequestBody AddProviderDto dto){
        return ResponseEntity.ok(parkingLotProviderService.registerParkingLotProvider(dto));
    }
    @PatchMapping("/providers/{id}")
    public ResponseEntity<ProviderResponseDto> updateParkingLotProvider(@RequestBody UpdateProviderRequestDto dto, @PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotProviderService.updateParkingLotProvider(dto,id));
    }
    @GetMapping("/providers/{id}")
    public ResponseEntity<ProviderResponseDto> getParkingLotProviderById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(parkingLotProviderService.getParkingLotProviderById(id));
    }
    @GetMapping("/providers/")
    public ResponseEntity<ProviderResponseDto> getParkingLotProviderById(){
        return ResponseEntity.ok(parkingLotProviderService.getParkingLotProviderById());
    }
    @GetMapping("/providers")
    public ResponseEntity<List<ProviderResponseDto>> getAllProviders(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return ResponseEntity.ok(parkingLotProviderService.getAllProviders(pageable));
    }

}
