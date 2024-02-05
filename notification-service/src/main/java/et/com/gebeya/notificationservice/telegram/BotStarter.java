package et.com.gebeya.notificationservice.telegram;


import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component

public class BotStarter extends TelegramLongPollingBot {
    private final String username;
    private final MessageBuilder messageBuilder;
    private final RedisService redisService;

    public BotStarter(@Value("${app.telegram.username}") String username,
                      @Value("${app.telegram.token}") String botToken,MessageBuilder messageBuilder, RedisService redisService)
    {
        super(botToken);
        this.username = username;
        this.messageBuilder=messageBuilder;
        this.redisService=redisService;
    }
    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if(update.hasMessage())
        {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            String text = message.getText();
            if(text.equals("/start"))
            {
                execute(messageBuilder.welcomeMessage(chatId));
            }
            else if(isPatternMatch(text))
            {
                redisService.setChatId(text,chatId);
                execute(messageBuilder.phoneNoResponse(chatId,text));
            }
            else{
                execute(messageBuilder.errorMessage(chatId));
            }


        }
    }

    public static boolean isPatternMatch(String input) {
        Pattern regex = Pattern.compile("^0[79]\\d{8}$");
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
