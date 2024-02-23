package et.com.gebeya.notificationservice.handler;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@Slf4j
public class CustomHandler implements WebSocketHandler {
    private static Map<String, WebSocketSession> sessionsByUserId = new ConcurrentHashMap<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        log.info("user id {}",userId);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
            log.info(message.toString());
    }

    @Override
    public void handleTransportError(@NotNull WebSocketSession session,@NotNull Throwable exception) throws Exception {
        log.error(exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
//        log.error(exception.getMessage());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
