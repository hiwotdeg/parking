package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.PriceRequestDto;
import et.com.gebeya.paymentservice.model.OperationHour;
import et.com.gebeya.paymentservice.service.OperationHourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final OperationHourService operationHourService;
    @GetMapping("/operation/{id}")
    public ResponseEntity<List<OperationHour>> getOperationHourById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(operationHourService.getOperationHoursById(id));
    }



    @PostMapping("/pricing")
    public ResponseEntity<BigDecimal> getPricing(@RequestBody PriceRequestDto request){
        return ResponseEntity.ok(operationHourService.dynamicPricing(request));
    }
}
