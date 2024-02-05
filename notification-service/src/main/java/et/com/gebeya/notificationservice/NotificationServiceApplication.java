package et.com.gebeya.notificationservice;

import et.com.gebeya.notificationservice.telegram.BotStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class NotificationServiceApplication {

    public static void main(String[] args) throws TelegramApiException {

        ConfigurableApplicationContext context = SpringApplication.run(NotificationServiceApplication.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(context.getBean("botStarter", BotStarter.class));
    }

}
