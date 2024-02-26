package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.VehicleRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.VehicleResponseDto;
import et.com.gebeya.parkinglotservice.exception.DriverIdNotFound;
import et.com.gebeya.parkinglotservice.exception.VehicleIdNotFound;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.Vehicle;
import et.com.gebeya.parkinglotservice.repository.DriverRepository;
import et.com.gebeya.parkinglotservice.repository.VehicleRepository;
import et.com.gebeya.parkinglotservice.repository.specification.DriverSpecification;
import et.com.gebeya.parkinglotservice.repository.specification.VehicleSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    public VehicleResponseDto addVehicle(VehicleRequestDto vehicleRequestDto){
        Integer driverId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vehicle vehicle = MappingUtil.vehicleRequestDtoToVehicle(vehicleRequestDto);
        Driver driver = getDriver(driverId);
        vehicle.setDriver(driver);
        vehicle = vehicleRepository.save(vehicle);
        return MappingUtil.vehicleToVehicleResponseDto(vehicle);
    }

    public VehicleResponseDto updateVehicle(VehicleRequestDto vehicleRequestDto, Integer vehicleId){
        Vehicle vehicle = getVehicle(vehicleId);
        vehicle = vehicleRepository.save(MappingUtil.updateVehicle(vehicleRequestDto,vehicle));
        return MappingUtil.vehicleToVehicleResponseDto(vehicle);
    }



    private Driver getDriver(Integer id) {
        List<Driver> driver = driverRepository.findAll(DriverSpecification.getDriverById(id));
        if (driver.isEmpty())
            throw new DriverIdNotFound("Driver is not found");
        return driver.get(0);
    }


    private Vehicle getVehicle(Integer id){
        List<Vehicle> vehicle = vehicleRepository.findAll(VehicleSpecification.getVehicleById(id));
        if(vehicle.isEmpty())
            throw new VehicleIdNotFound("Vehicle is not found");
        return vehicle.get(0);
    }


}
