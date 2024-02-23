package et.com.gebeya.notificationservice.service;

import et.com.gebeya.notificationservice.dto.MessageDto;
import et.com.gebeya.notificationservice.handler.NotificationHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final NotificationHandler notificationHandler;
    public void sendPushNotification(MessageDto messageDto) {
       notificationHandler.message(messageDto);
    }

}
