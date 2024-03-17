package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.BalanceRequestDto;
import et.com.gebeya.paymentservice.dto.request.TransferBalanceRequestDto;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.dto.response.ResponseModel;
import et.com.gebeya.paymentservice.service.WalletService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @Hidden
    @PostMapping("/driver")
    public ResponseEntity<BalanceResponseDto> createBalanceForDriver(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(walletService.createWalletForDriver(dto));
    }
    @Hidden
    @PostMapping("/provider")
    public ResponseEntity<BalanceResponseDto> createBalanceForProvider(@RequestBody BalanceRequestDto dto){
        return ResponseEntity.ok(walletService.createWalletForProvider(dto));
    }
    @Hidden
    @DeleteMapping("/driver/{id}")
    public ResponseEntity<ResponseModel> deleteBalanceForDriver(@PathVariable("id") Integer id){
        return ResponseEntity.ok(walletService.deleteWalletForDriver(id));
    }
    @Hidden
    @DeleteMapping("/provider/{id}")
    public ResponseEntity<ResponseModel> deleteBalanceForProvider(@PathVariable("id") Integer id){
        return ResponseEntity.ok(walletService.deleteWalletForProvider(id));
    }

    @Hidden
    @PostMapping("/transfer")
    public ResponseEntity<BalanceResponseDto> transferBalance(@RequestBody TransferBalanceRequestDto dto){
        return ResponseEntity.ok(walletService.transferBalance(dto));
    }

    @GetMapping("/balance")
    public ResponseEntity<BalanceResponseDto> checkBalance(){
        return ResponseEntity.ok(walletService.getWalletBalance());
    }
    @Hidden
    @GetMapping("/driver_balance/{id}")
    public ResponseEntity<BalanceResponseDto> checkBalanceForDriverId(@PathVariable("id") Integer driverId){
        return ResponseEntity.ok(walletService.checkBalanceForDriver(driverId));
    }

}
