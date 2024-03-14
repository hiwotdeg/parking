package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.TransferBalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.WithdrawalRequestDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.service.CouponManagementService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class CouponManagementController {
    private final CouponManagementService couponManagementService;

    @Hidden
    @PostMapping("/driver")
    public ResponseEntity<BalanceResponseDto> createBalanceForDriver(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.createBalanceForDriver(dto));
    }
    @Hidden
    @PostMapping("/provider")
    public ResponseEntity<BalanceResponseDto> createBalanceForProvider(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.createBalanceForProvider(dto));
    }
    @Hidden
    @DeleteMapping("/driver/{id}")
    public ResponseEntity<Map<String,String>> deleteBalanceForDriver(@PathVariable("id") Integer id){
        return ResponseEntity.ok(couponManagementService.deleteCouponBalanceForDriver(id));
    }
    @Hidden
    @DeleteMapping("/provider/{id}")
    public ResponseEntity<Map<String,String>> deleteBalanceForProvider(@PathVariable("id") Integer id){
        return ResponseEntity.ok(couponManagementService.deleteCouponBalanceForProvider(id));
    }
    @PostMapping("/withdrawal")
    public ResponseEntity<BalanceResponseDto> withdrawBalanceFromProvider(@RequestBody WithdrawalRequestDto dto){
        return ResponseEntity.ok(couponManagementService.withdrawalBalanceForProvider(dto));
    }
//    @PostMapping("/deposit")
//    public ResponseEntity<BalanceResponseDto> depositBalanceForDriver(@RequestBody BalanceRequestDto dto){
//        return ResponseEntity.ok(couponManagementService.depositBalanceForDriver(dto));
//    }
    @Hidden
    @PostMapping("/transfer")
    public ResponseEntity<BalanceResponseDto> transferBalance(@RequestBody TransferBalanceRequestDto dto){
        return ResponseEntity.ok(couponManagementService.transferBalance(dto));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDto> checkBalance(){
        return ResponseEntity.ok(couponManagementService.checkBalance());
    }
    @Hidden
    @GetMapping("/driver_balance/{id}")
    public ResponseEntity<BalanceResponseDto> checkBalanceForDriverId(@PathVariable("id") Integer driverId){
        return ResponseEntity.ok(couponManagementService.checkBalanceForDriver(driverId));
    }

}
