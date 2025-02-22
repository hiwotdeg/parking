package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.*;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ResponseModel;
import et.com.gebeya.parkinglotservice.exception.MoreThanOneProviderException;
import et.com.gebeya.parkinglotservice.exception.ParkingLotIdNotFound;
import et.com.gebeya.parkinglotservice.exception.ProviderIdNotFound;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.repository.ParkingLotImageRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotProviderRepository;
import et.com.gebeya.parkinglotservice.repository.ParkingLotRepository;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotProviderSpecification;
import et.com.gebeya.parkinglotservice.repository.specification.ParkingLotSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static et.com.gebeya.parkinglotservice.util.Constant.*;

@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotProviderRepository parkingLotProviderRepository;
    private final ParkingLotImageRepository parkingLotImageRepository;
    private final KafkaTemplate<String, DeleteLocationRequestDto> deleteLocationRequestDtoKafkaTemplate;
    private final KafkaTemplate<String, AddLocationRequestDto> addLocationRequestDtoKafkaTemplate;
    private final KafkaTemplate<String, UpdateLocationRequestDto> updateLocationRequestDtoKafkaTemplate;

    @Transactional
    public ParkingLotResponseDto addParkingLot(AddParkingLotDto dto) {
        ParkingLot parkingLot = MappingUtil.mapAddParkingLotToParkingLot(dto);
        parkingLot.setRating(5.0f);
        parkingLot.setAvailableSlot(parkingLot.getCapacity());
        UserDto providerId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        parkingLot.setParkingLotProvider(getProvider(providerId.getId()));
        if (!parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotByProviderId(parkingLot.getParkingLotProvider().getId())).isEmpty())
            throw new MoreThanOneProviderException("one provider can add one parking lot only");
        parkingLot = parkingLotRepository.save(parkingLot);
        AddLocationRequestDto addLocationRequestDto = AddLocationRequestDto.builder()
                .id(parkingLot.getId())
                .title(parkingLot.getName())
                .address(parkingLot.getAddress())
                .longitude(parkingLot.getLongitude())
                .latitude(parkingLot.getLatitude())
                .build();
        addLocationRequestDtoKafkaTemplate.send(ADD_LOCATION, addLocationRequestDto);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    @Transactional
    public ParkingLotResponseDto updateParkingLot(UpdateParkingLotDto dto, Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        UpdateLocationRequestDto updateLocationRequestDto = UpdateLocationRequestDto.builder().id(parkingLot.getId()).existingTitle(parkingLot.getName()).existingAddress(parkingLot.getAddress()).build();
        parkingLot = parkingLotRepository.save(MappingUtil.updateParkingLot(parkingLot, dto));
        updateLocationRequestDto.setLongitude(parkingLot.getLongitude());
        updateLocationRequestDto.setLatitude(parkingLot.getLatitude());
        updateLocationRequestDto.setNewAddress(parkingLot.getAddress());
        updateLocationRequestDto.setNewTitle(parkingLot.getName());
        updateLocationRequestDtoKafkaTemplate.send(UPDATE_LOCATION, updateLocationRequestDto);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    public ParkingLotResponseDto getParkingLotById(Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    @Transactional
    public ResponseModel deleteParkingLot(Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        parkingLot.setIsActive(false);
        parkingLotRepository.save(parkingLot);
        DeleteLocationRequestDto deleteLocationRequestDto = DeleteLocationRequestDto.builder()
                .id(parkingLot.getId())
                .title(parkingLot.getName())
                .address(parkingLot.getAddress()).build();
        deleteLocationRequestDtoKafkaTemplate.send(DELETE_LOCATION, deleteLocationRequestDto);
        return ResponseModel.builder().message("parking lot deleted successfully").build();
    }

    ParkingLot getParkingLot(Integer id) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if (parkingLots.isEmpty())
            throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }

    ParkingLot getParkingLotByProviderId(Integer id) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotByProviderId(id));
        if (parkingLots.isEmpty())
            throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }

    public ParkingLotResponseDto getMyParkingLotByProviderId() {
        UserDto providerId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ParkingLot parkingLot = getParkingLotByProviderId(providerId.getId());
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    private ParkingLotProvider getProvider(Integer id) {
        List<ParkingLotProvider> providers = parkingLotProviderRepository.findAll(ParkingLotProviderSpecification.getProviderById(id));
        if (providers.isEmpty())
            throw new ProviderIdNotFound("provider id not found");
        return providers.get(0);
    }

    public List<ParkingLotResponseDto> getAllParkingLots(Pageable pageable) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getAllParkingLot(), pageable).stream().toList();
        return MappingUtil.listOfParkingLotToListOfParkingLotResponseDto(parkingLots);
    }


}
