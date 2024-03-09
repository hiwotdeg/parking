package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.DeleteLocationRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.VehicleRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.VehicleResponseDto;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.exception.VehicleIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.Vehicle;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.VehicleRepository;
import et.com.gebeya.parkinglotservice.repository.specification.DriverSpecification;
import et.com.gebeya.parkinglotservice.repository.specification.VehicleSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static et.com.gebeya.parkinglotservice.util.Constant.DELETE_LOCATION;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DriverService driverService;

    public VehicleResponseDto addVehicle(VehicleRequestDto vehicleRequestDto) {
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vehicle vehicle = MappingUtil.vehicleRequestDtoToVehicle(vehicleRequestDto);
        Driver driver = driverService.getDriver(driverId);
        vehicle.setDriver(driver);
        vehicle = vehicleRepository.save(vehicle);
        return MappingUtil.vehicleToVehicleResponseDto(vehicle);
    }

    public VehicleResponseDto updateVehicle(VehicleRequestDto vehicleRequestDto, Integer vehicleId) {
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle = vehicleRepository.save(MappingUtil.updateVehicle(vehicleRequestDto, vehicle));
        return MappingUtil.vehicleToVehicleResponseDto(vehicle);
    }

    Vehicle getVehicle(Integer id) {
        List<Vehicle> vehicle = vehicleRepository.findAll(VehicleSpecification.getVehicleById(id));
        if (vehicle.isEmpty())
            throw new VehicleIdNotFound("Vehicle is not found");
        return vehicle.get(0);
    }

    public List<VehicleResponseDto> getVehiclesByDriverId() {
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Vehicle> vehicle = vehicleRepository.findAll(VehicleSpecification.getVehicleByDriverId(driverId));
        return MappingUtil.vehicleToListOfVehicleResponseDto(vehicle);
    }

    public VehicleResponseDto getVehiclesByVehicleId(Integer id) {
        List<Vehicle> vehicle = vehicleRepository.findAll(VehicleSpecification.getVehicleById(id));
        if (vehicle.isEmpty())
            throw new VehicleIdNotFound("Vehicle is not found");
        return MappingUtil.vehicleToVehicleResponseDto(vehicle.get(0));
    }


    public Map<String, String> deleteVehicleById(Integer id) {
        Vehicle vehicle = getVehicle(id);
        vehicle.setIsActive(true);
        vehicleRepository.save(vehicle);
        Map<String, String> response = new HashMap<>();
        response.put("message", "vehicle deleted successfully");
        return response;
    }


}
