package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.AddUserResponse;
import et.com.gebeya.parkinglotservice.model.Customer;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping("/register")
    public ResponseEntity<AddUserResponse> registerCustomer(@RequestBody Customer customer){
        return ResponseEntity.ok(customerService.registerCustomer(customer));
    }
}
