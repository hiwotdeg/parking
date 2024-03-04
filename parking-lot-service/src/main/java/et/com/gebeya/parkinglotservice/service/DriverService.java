package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.requestdto.BalanceRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateDriverRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.DriverResponseDto;
import et.com.gebeya.parkinglotservice.exception.AuthException;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.specification.DriverSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService {
    private final WebClient.Builder webClientBuilder;
    private final DriverRepository driverRepository;

    @Transactional
    public AddUserResponse registerDriver(AddDriverRequestDto dto) {

        Driver driver = MappingUtil.mapAddDiverRequestDtoToDriver(dto);
        driver = driverRepository.save(driver);
        AddUserRequest addUserRequest = MappingUtil.mapCustomerToAddUserRequest(driver);
        Mono<ResponseEntity<AddUserResponse>> responseMono = webClientBuilder.build()
                .post()
                .uri("http://AUTH-SERVICE/api/v1/auth/addUser")
                .body(Mono.just(addUserRequest), AddUserRequest.class)
                .retrieve()
                .toEntity(AddUserResponse.class);

        BalanceRequestDto requestDto = BalanceRequestDto.builder().userId(driver.getId()).amount(BigDecimal.valueOf(0.0)).build();
        BalanceResponseDto responseDto = createBalance(requestDto);
        log.info("Response from Payment micro service==> {}", responseDto);
        return responseMono.blockOptional()
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new AuthException("Error occurred during generating of token"));
    }

    public DriverResponseDto updateDriver(UpdateDriverRequestDto dto, Integer id) {
        Driver driver = getDriver(id);
        driver = MappingUtil.mapUpdateDiverRequestDtoToDriver(dto, driver);
        return MappingUtil.mapDriverToDriverResponseDto(driver);
    }

    public DriverResponseDto getDriverById(Integer id) {
        Driver driver = getDriver(id);
        return MappingUtil.mapDriverToDriverResponseDto(driver);
    }

    public DriverResponseDto getDriverById(){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getDriverById(driverId);
    }

    Driver getDriver(Integer id) {
        List<Driver> driver = driverRepository.findAll(DriverSpecification.getDriverById(id));
        if (driver.isEmpty())
            throw new DriverIdNotFound("Driver is not found");
        return driver.get(0);
    }

    private BalanceResponseDto createBalance(BalanceRequestDto balanceRequestDto){
        return webClientBuilder.build().post()
                .uri("http://PAYMENT-SERVICE/api/v1/payment/driver")
                .bodyValue(balanceRequestDto)
                .retrieve()
                .bodyToMono(BalanceResponseDto.class)
                .block();

    }
}
