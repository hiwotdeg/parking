package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.AddUserResponse;
import et.com.gebeya.parkinglotservice.model.Customer;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.repository.CustomerRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final WebClient.Builder webClientBuilder;
    private final CustomerRepository customerRepository;
    private final ParkingLotProviderRepository parkingLotProviderRepository;

    public AddUserResponse registerParkingLotProvider(ParkingLotProvider parkingLotProvider){
        ParkingLotProvider saveParkingLotProvider = parkingLotProviderRepository.save(parkingLotProvider);
        AddUserRequest addUserRequest = MappingUtil.mapParkingLotProviderToAddUserRequest(saveParkingLotProvider);
        Mono<ResponseEntity<AddUserResponse>> responseMono = webClientBuilder.build()
                .post()
                .uri("http://AUTH-SERVICE/api/v1/auth/addUser") // Replace with the actual URL
                .body(Mono.just(addUserRequest), AddUserRequest.class)
                .retrieve()
                .toEntity(AddUserResponse.class);

        return responseMono.block().getBody();
//        return Objects.requireNonNull(responseMono.block()).getBody();

    }

}
