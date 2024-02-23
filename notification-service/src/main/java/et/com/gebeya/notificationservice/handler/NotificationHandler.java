package et.com.gebeya.notificationservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.notificationservice.dto.MessageDto;
import et.com.gebeya.notificationservice.service.RedisService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationHandler implements WebSocketHandler {
    private static Map<String, WebSocketSession> sessions= new ConcurrentHashMap<>();
    private final RedisService redisService;
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = (String) session.getAttributes().get("userId");
        sessions.put(userId,session);
        log.info("Welcome {}",userId);
        var messageDto = redisService.getMessage(userId);
        if(messageDto!=null)
            message(messageDto);


    }


    public WebSocketSession getSessionByUserId(String userId) {
        return sessions.get(userId);
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
        String userId = (String) session.getAttributes().get("userId");
        sessions.remove(userId);
        log.info(String.valueOf(sessions.size()));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void message(MessageDto messageDto){
        try{
            WebSocketSession session = getSessionByUserId(messageDto.getReceiverId());
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String,String> sendData = new HashMap<>();
            sendData.put("topic", messageDto.getTitle());
            sendData.put("body", messageDto.getBody());
            sendData.put("type", messageDto.getType());
            String jsonMessage = objectMapper.writeValueAsString(sendData);
            if(session==null)
            {
                redisService.setMessage(messageDto.getReceiverId(),messageDto);
            }
            else{
                session.sendMessage(new TextMessage(jsonMessage));
                redisService.deleteKey(messageDto.getReceiverId());
            }
        }
        catch (IOException exception)
        {
            log.error(exception.getMessage());
        }
    }
}
