package et.com.gebeya.geolocationservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

public class SwaggerConfig {
    @Value("${openapi.service.url}")
    private String gatewayUrl;

    @Bean
    public OpenAPI openAPIConfiguration() {
        return new OpenAPI()
                .servers(List.of(new Server().url(gatewayUrl).description("API Gateway"))).info(
                        new Info().title("Parking Lot Locator App REST API Documentation")
                                .version("1.0")
                                .description("Geo Location Service Documentation"));

    }

}
