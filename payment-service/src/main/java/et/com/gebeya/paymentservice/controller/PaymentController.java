package et.com.gebeya.paymentservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.paymentservice.dto.request.DepositDto;
import et.com.gebeya.paymentservice.dto.request.MpesaB2CResponse;
import et.com.gebeya.paymentservice.dto.request.MpesaStkCallback;
import et.com.gebeya.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/mpesa/b2cresults")
    public ResponseEntity<MpesaB2CResponse> handleResponse(@RequestBody String responseBody) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        MpesaB2CResponse response = objectMapper.readValue(responseBody, MpesaB2CResponse.class);
        Integer resultCode = response.getResult().getResultCode();
        if (resultCode == 0) {
            log.info(response.toString());
            return ResponseEntity.ok(response);
        } else {
            log.error(response.toString());
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/mpesa/stkcallback")
    public ResponseEntity<Void> handleStkCallback(@RequestBody MpesaStkCallback request) {
        paymentService.stkListener(request);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/deposit")
    public ResponseEntity<Map<String, String>> depositRequesterDriver(@RequestBody DepositDto dto) {
        return ResponseEntity.ok(paymentService.requestDeposit(dto));
    }
}
