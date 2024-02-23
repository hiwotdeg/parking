package et.com.gebeya.apigateway.filter;

import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/api/v1/auth/**",
            "/eureka",
            "/eureka/**",
            "/actuator/info",
            "/gateway/actuator",
            "/gateway/actuator/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/actuator",
            "/actuator/**",
            "/v3/api-docs/parking-lot",
            "/v3/api-docs/geo-location",
            "/v3/api-docs/payment",
            "/api/v1/parking-lot/sendMessage"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    // Ensure the path matches exactly, not just containing the substring
                    .noneMatch(uri -> request.getURI().getPath().equals(uri))
                    // Additionally check for POST method for specific URLs
                    && !(request.getURI().getPath().equals("/api/v1/parking-lot/providers") && request.getMethod().equals(HttpMethod.POST))
                    && !(request.getURI().getPath().equals("/api/v1/parking-lot/drivers") && request.getMethod().equals(HttpMethod.POST));

}
