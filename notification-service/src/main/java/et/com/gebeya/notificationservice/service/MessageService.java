package et.com.gebeya.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.notificationservice.dto.MessageDto;
import et.com.gebeya.notificationservice.handler.CustomHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final CustomHandler customHandler;
    public void sendPushNotification(MessageDto messageDto) {
       customHandler.message(messageDto);
    }

}
