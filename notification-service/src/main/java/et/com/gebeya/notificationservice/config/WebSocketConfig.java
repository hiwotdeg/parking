package et.com.gebeya.notificationservice.config;

import et.com.gebeya.notificationservice.handler.NotificationChannelInterceptor;
import et.com.gebeya.notificationservice.handler.NotificationHandler;
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
    private final NotificationChannelInterceptor notificationChannelInterceptor;
    private final NotificationHandler notificationHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(notificationHandler, "/notification-ws").setAllowedOrigins("*")
                .addInterceptors(notificationChannelInterceptor)
                .setHandshakeHandler(new DefaultHandshakeHandler());
    }
}
