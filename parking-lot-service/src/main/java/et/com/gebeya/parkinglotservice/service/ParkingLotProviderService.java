package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.*;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.BalanceResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ProviderResponseDto;
import et.com.gebeya.parkinglotservice.exception.AuthException;
import et.com.gebeya.parkinglotservice.exception.ProviderIdNotFound;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotProviderSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingLotProviderService {
    private final BalanceService balanceService;
    private final ParkingLotProviderRepository parkingLotProviderRepository;
    private final AuthService authService;
    @Transactional
    public AddUserResponse registerParkingLotProvider(AddProviderDto dto) {
        Integer pId = null;
        try{
            ParkingLotProvider provider = MappingUtil.mapAddProviderDtoToParkingLotProvider(dto);
            provider = parkingLotProviderRepository.save(provider);
            pId = provider.getId();
            AddUserRequest addUserRequest = MappingUtil.mapParkingLotProviderToAddUserRequest(provider);
            Mono<ResponseEntity<AddUserResponse>> responseMono = authService.getAuthServiceResponseEntityMono(addUserRequest);
            BalanceRequestDto requestDto = BalanceRequestDto.builder().userId(provider.getId()).amount(BigDecimal.valueOf(0.0)).build();
            BalanceResponseDto responseDto = balanceService.createBalanceForProvider(requestDto);
            log.info("Response from Payment micro service==> {}", responseDto);
            return responseMono.blockOptional()
                    .map(ResponseEntity::getBody)
                    .orElseThrow(() -> new AuthException("Error occurred during generating of token"));
        }
        catch (Exception e){
            if(pId!=null)
            {
                log.info("delete the coupon balance ==>{}",pId);
                balanceService.deleteBalanceForProvider(pId);
            }
            throw e;
        }

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

    public ProviderResponseDto getMyParkingLotProviderById()
    {
        UserDto providerId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getParkingLotProviderById(providerId.getId());
    }
    public List<ProviderResponseDto> getAllProviders(Pageable pageable){
        List<ParkingLotProvider> providers = parkingLotProviderRepository.findAll(ParkingLotProviderSpecification.getAllProviders(),pageable).stream().toList();
        return MappingUtil.listOfProviderToListOfProviderResponseDto(providers);
    }
    private ParkingLotProvider getParkingLotProvider(Integer id){
        List<ParkingLotProvider> providers = parkingLotProviderRepository.findAll(ParkingLotProviderSpecification.getProviderById(id));
        if (providers.isEmpty())
            throw new ProviderIdNotFound("provider id not found");
        return providers.get(0);
    }

}
