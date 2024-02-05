package et.com.gebeya.notificationservice.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramService    {
    private final MessageBuilder messageBuilder;
    private final RedisService redisService;
    private final BotStarter botStarter;

    public void sendMessage(String key, String message){
        long chatId = redisService.getChatId(key);
        botStarter.sendMessage(chatId, message);
    }
}
