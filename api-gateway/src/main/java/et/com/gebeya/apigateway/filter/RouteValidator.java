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
            "/api/v1/parking-lot/provider",
            "/api/v1/parking-lot/driver",
            "/eureka",
            "/eureka/**",
            "/actuator/info",
            "/gateway/actuator",
            "/gateway/actuator/**",
            "/actuator/**",
            "webjars/**",
            "/swagger-ui/**",
            "/v3/**"
    );

    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    // Ensure the path matches exactly, not just containing the substring
                    .noneMatch(uri -> request.getURI().getPath().equals(uri))
                    // Additionally check for POST method for specific URLs
                    && !(request.getURI().getPath().equals("/api/v1/parking-lot/provider") && request.getMethod().equals(HttpMethod.POST))
                    && !(request.getURI().getPath().equals("/api/v1/parking-lot/driver") && request.getMethod().equals(HttpMethod.POST));

}
