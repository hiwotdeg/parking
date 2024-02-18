package et.com.gebeya.paymentservice.controller;

import et.com.gebeya.paymentservice.dto.request.AddOperationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
public class OperationHourController {
    @PostMapping("/operation")
    public ResponseEntity<> addOperation(@RequestBody AddOperationRequestDto request){

    }
}
