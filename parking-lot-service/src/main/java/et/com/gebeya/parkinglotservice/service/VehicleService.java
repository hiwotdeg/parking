package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;


}
