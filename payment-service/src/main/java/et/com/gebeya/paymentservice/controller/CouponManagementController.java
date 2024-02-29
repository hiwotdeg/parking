package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.service.CouponManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class CouponManagementController {
    private final CouponManagementService couponManagementService;

    @PostMapping("/driver")
    public ResponseEntity<BalanceResponseDto> createBalanceForDriver(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.createBalanceForDriver(dto));
    }
    @PostMapping("/provider")
    public ResponseEntity<BalanceResponseDto> createBalanceForProvider(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.createBalanceForProvider(dto));
    }
    @PostMapping("/withdrawal")
    public ResponseEntity<BalanceResponseDto> withdrawBalanceFromProvider(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.withdrawalBalanceForProvider(dto));
    }
    @PostMapping("/deposit")
    public ResponseEntity<BalanceResponseDto> depositBalanceForDriver(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.depositBalanceForDriver(dto));
    }
}
