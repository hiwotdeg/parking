package et.com.gebeya.notificationservice.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class BotStarter extends TelegramLongPollingBot {
    private final String username;
    private final MessageBuilder messageBuilder;

    public BotStarter(@Value("${app.telegram.username}") String username,
                      @Value("${app.telegram.token}") String botToken,MessageBuilder messageBuilder)
    {
        super(botToken);
        this.username = username;
        this.messageBuilder=messageBuilder;
    }
    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
