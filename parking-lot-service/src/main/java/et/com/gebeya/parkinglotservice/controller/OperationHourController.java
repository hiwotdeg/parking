package et.com.gebeya.parkinglotservice.controller;


import et.com.gebeya.parkinglotservice.dto.requestdto.AddOperationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.OperationHourDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.OperationHourResponseDto;
import et.com.gebeya.parkinglotservice.service.OperationHourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class OperationHourController {
    private final OperationHourService operationHourService;

    @PostMapping("/lots/{parkingId}/operation-hours")
    public ResponseEntity<List<OperationHourResponseDto>> addOperation(@RequestBody List<OperationHourDto> request, @PathVariable("parkingId")Integer parkingId) {
        return ResponseEntity.ok(operationHourService.addOperationHour(request, parkingId));
    }

    @PatchMapping("")

    @GetMapping("/operation-hours/{id}")
    public ResponseEntity<List<OperationHourResponseDto>> getOperationHourById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(operationHourService.getOperationHoursById(id));
    }
}
