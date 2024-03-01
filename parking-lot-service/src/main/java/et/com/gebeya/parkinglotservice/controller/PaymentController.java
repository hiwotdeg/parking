package et.com.gebeya.parkinglotservice.controller;


import et.com.gebeya.parkinglotservice.dto.requestdto.PriceRequestDto;
import et.com.gebeya.parkinglotservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/pricing")
    public ResponseEntity<BigDecimal> getPricing(@ModelAttribute PriceRequestDto request){
        return ResponseEntity.ok(paymentService.dynamicPricing(request));
    }
}
