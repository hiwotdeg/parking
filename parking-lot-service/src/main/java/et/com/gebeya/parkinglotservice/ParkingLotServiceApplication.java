package et.com.gebeya.parkinglotservice;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot REST API Documentation",
                description = "Spring Boot REST API Documentation",
                version = "1.0",
                contact = @Contact(
                        name = "Ramesh",
                        email = "javaguides.net@gmail.com",
                        url = "https://www.javaguides.net"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.javaguides.net/license"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot User Management Documentation",
                url = "https://www.javaguides.net/user_management.html"
        )
)
public class ParkingLotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingLotServiceApplication.class, args);
    }

}
