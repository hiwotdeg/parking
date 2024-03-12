package et.com.gebeya.apigateway.filter;

import et.com.gebeya.apigateway.dto.TokenDto;
import et.com.gebeya.apigateway.dto.ValidationResponseDto;
import et.com.gebeya.apigateway.exception.HeaderNotFound;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;
@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private final RouteValidator validator;
    private final WebClient.Builder webClientBuilder;

    public static class Config {

    }
    public AuthenticationFilter(RouteValidator validator, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.validator = validator;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                // Header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new HeaderNotFound("Missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                TokenDto token = TokenDto.builder().token(authHeader).build();
                return validateAuthorization(token)
                        .flatMap(response -> {
                            ServerHttpRequest mutatedHttpRequest = exchange.getRequest().mutate()
                                    .header("Role", response.getRole().toString())
                                    .header("RoleId",response.getRoleId().toString())
                                    .build();
                            ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedHttpRequest).build();
                            return chain.filter(mutatedExchange);
                        });
            }
            return chain.filter(exchange);
        });
    }


    public Mono<ValidationResponseDto> validateAuthorization(TokenDto token) {
        return webClientBuilder.build()
                .post()
                .uri("http://Auth-Service/api/v1/auth/validate")
                .header("Content-Type", "application/json")
                .body(Mono.just(token), TokenDto.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    HttpStatusCode status = response.statusCode();
                    if(status.is4xxClientError())
                        return Mono.error(new ValidationException("UNAUTHORIZED"));
                    else{
                        return Mono.error(new RuntimeException("UNKNOWN ERROR OCCURRED"));
                    }

                })
                .toEntity(ValidationResponseDto.class)
                .flatMap(responseEntity -> Mono.just(Objects.requireNonNull(responseEntity.getBody())));
    }





}
