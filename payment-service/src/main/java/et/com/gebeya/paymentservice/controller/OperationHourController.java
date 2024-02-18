package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.AddOperationRequestDto;
import et.com.gebeya.paymentservice.dto.response.OperationHourResponseDto;
import et.com.gebeya.paymentservice.service.OperationHourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class OperationHourController {
    private final OperationHourService operationHourService;

    @PostMapping("/operation")
    public ResponseEntity<List<OperationHourResponseDto>> addOperation(@RequestBody AddOperationRequestDto request) {
        return ResponseEntity.ok(operationHourService.addOperationHour(request));
    }
}
