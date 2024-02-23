package et.com.gebeya.notificationservice.config;

import et.com.gebeya.notificationservice.handler.CustomChannelInterceptor;
import et.com.gebeya.notificationservice.handler.CustomHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final CustomChannelInterceptor customChannelInterceptor;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new CustomHandler(), "/notification-ws").setAllowedOrigins("*")
                .addInterceptors(customChannelInterceptor)
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }
}
