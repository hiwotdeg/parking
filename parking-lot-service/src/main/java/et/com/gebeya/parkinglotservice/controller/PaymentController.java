package et.com.gebeya.parkinglotservice.controller;


import et.com.gebeya.parkinglotservice.dto.requestdto.PriceRequestDto;
import et.com.gebeya.parkinglotservice.service.PricingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class PaymentController {
    private final PricingService pricingService;

    @GetMapping("/lots/{parkingLotId}/pricing") // drivers
    public ResponseEntity<BigDecimal> getPricing(@ModelAttribute PriceRequestDto request, @PathVariable("parkingLotId")Integer parkingLotId){
        return ResponseEntity.ok(pricingService.dynamicPricing(request,parkingLotId));
    }
}
