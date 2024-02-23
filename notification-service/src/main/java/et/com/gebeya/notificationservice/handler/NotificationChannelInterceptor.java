package et.com.gebeya.notificationservice.handler;

import et.com.gebeya.notificationservice.dto.TokenDto;
import et.com.gebeya.notificationservice.dto.ValidationResponseDto;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationChannelInterceptor implements HandshakeInterceptor {

    private final WebClient.Builder webClientBuilder;

    @Override
    public boolean beforeHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpHeaders headers = servletRequest.getHeaders();

            String token = headers.getFirst("Authorization");
            log.info(token);
            if (token != null) {
                String id = isValidToken(token);
                if (id != null) {
                    attributes.put("userId", id);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    @Override
    public void afterHandshake(@NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response, @NotNull WebSocketHandler wsHandler, Exception exception) {
        log.info("handshake done");
    }

    private String isValidToken(String tokenValue) {
        TokenDto token = TokenDto.builder().token(tokenValue).build();
        Optional<ValidationResponseDto> authorizationResponse = validateAuthorization(token).blockOptional();

        if (authorizationResponse.isPresent()) {
            ValidationResponseDto response = authorizationResponse.get();
            return response.getRole() + "_" + response.getRoleId();
        } else {
            return null;
        }
    }

    public Mono<ValidationResponseDto> validateAuthorization(TokenDto token) {
        return webClientBuilder.build()
                .post()
                .uri("http://localhost:8080/api/v1/auth/validate")
                .header("Content-Type", "application/json")
                .body(Mono.just(token), TokenDto.class)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> {
                    HttpStatusCode status = response.statusCode();
                    if (status.is4xxClientError())
                        return Mono.error(new ValidationException("UNAUTHORIZED"));
                    else {
                        return Mono.error(new RuntimeException("UNKNOWN ERROR OCCURRED"));
                    }

                })
                .toEntity(ValidationResponseDto.class)
                .flatMap(responseEntity -> Mono.just(Objects.requireNonNull(responseEntity.getBody())));
    }


}
