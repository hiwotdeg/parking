package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.PriceRequestDto;
import et.com.gebeya.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/pricing")
    public ResponseEntity<BigDecimal> getPricing(@ModelAttribute PriceRequestDto request){
        return ResponseEntity.ok(paymentService.dynamicPricing(request));
    }
}
