package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddLocationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.AddParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.DeleteLocationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateParkingLotDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
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
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static et.com.gebeya.parkinglotservice.util.Constant.ADD_LOCATION;
import static et.com.gebeya.parkinglotservice.util.Constant.DELETE_LOCATION;

@Service
@RequiredArgsConstructor
public class ParkingLotService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotProviderRepository parkingLotProviderRepository;
    private final ParkingLotImageRepository parkingLotImageRepository;
    private final KafkaTemplate<String, DeleteLocationRequestDto> deleteLocationRequestDtoKafkaTemplate;
    private final KafkaTemplate<String, AddLocationRequestDto> addLocationRequestDtoKafkaTemplate;

    @Transactional
    public ParkingLotResponseDto addParkingLot(AddParkingLotDto dto) {
        ParkingLot parkingLot = MappingUtil.mapAddParkingLotToParkingLot(dto);
        parkingLot.setRating(5.0f);
        parkingLot.setAvailableSlot(parkingLot.getCapacity());
        Integer id = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        parkingLot.setParkingLotProvider(getProvider(id));
        if (!parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotByProviderId(parkingLot.getParkingLotProvider().getId())).isEmpty())
            throw new MoreThanOneProviderException("one provider can add one parking lot only");
        parkingLot = parkingLotRepository.save(parkingLot);
        AddLocationRequestDto addLocationRequestDto = AddLocationRequestDto.builder()
                .id(parkingLot.getId())
                .longitude(parkingLot.getLongitude())
                .latitude(parkingLot.getLatitude())
                .build();
        addLocationRequestDtoKafkaTemplate.send(ADD_LOCATION, addLocationRequestDto);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    @Transactional
    public ParkingLotResponseDto updateParkingLot(UpdateParkingLotDto dto, Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        parkingLot = parkingLotRepository.save(MappingUtil.updateParkingLot(parkingLot, dto));
        AddLocationRequestDto addLocationRequestDto = AddLocationRequestDto.builder()
                .id(parkingLot.getId())
                .longitude(parkingLot.getLongitude())
                .latitude(parkingLot.getLatitude())
                .build();
        addLocationRequestDtoKafkaTemplate.send(ADD_LOCATION, addLocationRequestDto);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    public ParkingLotResponseDto getParkingLotById(Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        return MappingUtil.parkingLotResponse(parkingLot);
    }

    @Transactional
    public Map<String, String> deleteParkingLot(Integer id) {
        ParkingLot parkingLot = getParkingLot(id);
        parkingLot.setIsActive(false);
        parkingLotRepository.save(parkingLot);
        deleteLocationRequestDtoKafkaTemplate.send(DELETE_LOCATION, DeleteLocationRequestDto.builder().id(id).build());
        Map<String, String> response = new HashMap<>();
        response.put("message", "parking lot deleted successfully");
        return response;
    }

    private ParkingLot getParkingLot(Integer id) {
        List<ParkingLot> parkingLots = parkingLotRepository.findAll(ParkingLotSpecification.getParkingLotById(id));
        if (parkingLots.isEmpty())
            throw new ParkingLotIdNotFound("parking lot id not found");
        return parkingLots.get(0);
    }

    private ParkingLotProvider getProvider(Integer id) {
        List<ParkingLotProvider> providers = parkingLotProviderRepository.findAll(ParkingLotProviderSpecification.getProviderById(id));
        if (providers.isEmpty())
            throw new ProviderIdNotFound("provider id not found");
        return providers.get(0);
    }

}
