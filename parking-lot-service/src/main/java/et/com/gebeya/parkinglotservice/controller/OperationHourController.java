package et.com.gebeya.parkinglotservice.controller;


import et.com.gebeya.parkinglotservice.dto.requestdto.OperationHourDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.OperationHourResponseDto;
import et.com.gebeya.parkinglotservice.service.OperationHourService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class OperationHourController {
    private final OperationHourService operationHourService;

    @PostMapping("/lots/{parkingLotId}/operation-hours") // providers
    public ResponseEntity<List<OperationHourResponseDto>> addOperation(@RequestBody List<OperationHourDto> request, @PathVariable("parkingLotId")Integer parkingId) {
        return ResponseEntity.ok(operationHourService.addOperationHour(request, parkingId));
    }

    @GetMapping("/lots/{parkingLotId}/operation-hours") // providers,drivers,admin
    public ResponseEntity<List<OperationHourResponseDto>> getOperationHourById(@PathVariable("parkingLotId") Integer parkingId){
        return ResponseEntity.ok(operationHourService.getOperationHoursByParkingLotId(parkingId));
    }

    @GetMapping("/lots/{parkingLotId}/operation-hours/{operationHourId}") // providers,drivers,admins
    public ResponseEntity<OperationHourResponseDto> getOperationHourById(@PathVariable("parkingLotId") Integer parkingId, @PathVariable("operationHourId") Integer operationHourId){
        return ResponseEntity.ok(operationHourService.getOperationHoursByOperationHourId(parkingId,operationHourId));
    }

    @DeleteMapping("/lots/{parkingLotId}/operation-hours/{operationHourId}") // providers
    public ResponseEntity<Map<String, String>> deleteOperation (@PathVariable("parkingLotId" ) Integer parkingId, @PathVariable("operationHourId") Integer operationId){
        return ResponseEntity.ok(operationHourService.deleteOperationHour(parkingId, operationId));
    }
}
