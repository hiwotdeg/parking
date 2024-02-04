package et.com.gebeya.apigateway.filter;

import et.com.gebeya.apigateway.dto.TokenDto;
import et.com.gebeya.apigateway.dto.ValidationResponseDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
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
                //header contains token or not
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                TokenDto token = TokenDto.builder().token(authHeader).build();
               ValidationResponseDto response = identityValidator(token).block();
//                System.out.println(response);
//                template.getForObject("http://AUTH-SERVICE//validate?name=" + authHeader, String.class);
//                ValidationResponseDto responseDto = validate(token);
                System.out.println(response.getRole());
            }
            return chain.filter(exchange);
        });


    }

    private Mono<ValidationResponseDto> identityValidator(TokenDto token) {


        WebClient.RequestHeadersSpec<?> headersSpec = webClientBuilder.build().post()
                .uri("http://AUTH-SERVICE/api/v1/auth/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(token);


        return headersSpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(ValidationResponseDto.class);
            } else if (response.statusCode().is4xxClientError()) {
                return response.createException()
                        .flatMap(Mono::error);
            } else {
                return response.createException()
                        .flatMap(Mono::error);
            }
        });


    }

}
