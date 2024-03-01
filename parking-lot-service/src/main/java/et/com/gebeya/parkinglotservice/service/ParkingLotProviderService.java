package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddProviderDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateProviderRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.ProviderResponseDto;
import et.com.gebeya.parkinglotservice.exception.AuthException;
import et.com.gebeya.parkinglotservice.exception.ProviderIdNotFound;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotProviderSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingLotProviderService {
    private final WebClient.Builder webClientBuilder;
    private final ParkingLotProviderRepository parkingLotProviderRepository;

    public AddUserResponse registerParkingLotProvider(AddProviderDto dto) {

        ParkingLotProvider provider = MappingUtil.mapAddProviderDtoToParkingLotProvider(dto);
        provider = parkingLotProviderRepository.save(provider);
        AddUserRequest addUserRequest = MappingUtil.mapParkingLotProviderToAddUserRequest(provider);
        Mono<ResponseEntity<AddUserResponse>> responseMono = webClientBuilder.build()
                .post()
                .uri("http://AUTH-SERVICE/api/v1/auth/addUser")
                .body(Mono.just(addUserRequest), AddUserRequest.class)
                .retrieve()
                .toEntity(AddUserResponse.class);

        return responseMono.blockOptional()
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new AuthException("Error occurred during generating of token"));
    }

    public ProviderResponseDto updateParkingLotProvider(UpdateProviderRequestDto dto, Integer id) {
        ParkingLotProvider provider = getParkingLotProvider(id);
        provider = MappingUtil.updateParkingLotProvider(dto, provider);
        provider = parkingLotProviderRepository.save(provider);
        return MappingUtil.mapParkingLotProviderToProviderResponseDto(provider);
    }

    public ProviderResponseDto getParkingLotProviderById(Integer id)
    {
        ParkingLotProvider provider = getParkingLotProvider(id);
        return MappingUtil.mapParkingLotProviderToProviderResponseDto(provider);
    }

    public ProviderResponseDto getParkingLotProviderById()
    {
        Integer providerId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getParkingLotProviderById(providerId);
    }
    private ParkingLotProvider getParkingLotProvider(Integer id){
        List<ParkingLotProvider> providers = parkingLotProviderRepository.findAll(ParkingLotProviderSpecification.getProviderById(id));
        if (providers.isEmpty())
            throw new ProviderIdNotFound("provider id not found");
        return providers.get(0);
    }
}
