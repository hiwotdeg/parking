package et.com.gebeya.parkinglotservice.controller;


import et.com.gebeya.parkinglotservice.dto.requestdto.AddOperationRequestDto;
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

    @PostMapping("/operation-hours")
    public ResponseEntity<List<OperationHourResponseDto>> addOperation(@RequestBody AddOperationRequestDto request) {
        return ResponseEntity.ok(operationHourService.addOperationHour(request));
    }
}
