package et.com.gebeya.notificationservice.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramService    {
    private final MessageBuilder messageBuilder;
    private final RedisService redisService;

    public void sendMessage(String key, String message){
        long chatId = redisService.getChatId(key);
        messageBuilder.sendMessage(chatId, message);
    }
}
