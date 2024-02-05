package et.com.gebeya.notificationservice.telegram;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static et.com.gebeya.notificationservice.telegram.Constants.*;

@Component
@RequiredArgsConstructor
public class MessageBuilder {
    public SendMessage welcomeMessage(Long chatId)
    {
        return SendMessage.builder().chatId(chatId)
                .text(WELCOME_MESSAGE)
                .build();
    }

    public SendMessage phoneNoResponse(Long chatId, String phoneNo)
    {
        return SendMessage.builder()
                .chatId(chatId)
                .text(RESPONSE_MESSAGE(phoneNo))
                .build();
    }

    public SendMessage errorMessage(Long chatId){
        return SendMessage.builder()
                .chatId(chatId)
                .text(ERROR_MESSAGE)
                .build();
    }

}
