package et.com.gebeya.parkinglotservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "Parking Lot Locator App REST API Documentation", description = "Parking-Lot Service Documentation", version = "1.0"))
public class ParkingLotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotServiceApplication.class, args);
    }

}
