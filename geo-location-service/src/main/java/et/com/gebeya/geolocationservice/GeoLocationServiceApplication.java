package et.com.gebeya.geolocationservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Parking Lot Locator App REST API Documentation", description = "Geo Location Service Documentation", version = "1.0"))
@EnableDiscoveryClient
public class GeoLocationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GeoLocationServiceApplication.class, args);
	}

}
