package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.AddUserResponse;
import et.com.gebeya.parkinglotservice.model.Customer;
import et.com.gebeya.parkinglotservice.repository.CustomerRepository;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final WebClient.Builder webClientBuilder;
    private final CustomerRepository customerRepository;

    public AddUserResponse registerCustomer(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        AddUserRequest addUserRequest = MappingUtil.mapCustomerToAddUserRequest(savedCustomer);
        Mono<ResponseEntity<AddUserResponse>> responseMono = webClientBuilder.build()
                .post()
                .uri("http://AUTH-SERVICE/api/v1/auth/addUser")
                .body(Mono.just(addUserRequest), AddUserRequest.class)
                .retrieve()
                .toEntity(AddUserResponse.class);

        return responseMono.block().getBody();

    }
}
