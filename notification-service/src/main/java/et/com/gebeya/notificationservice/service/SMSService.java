package et.com.gebeya.notificationservice.service;

import et.com.gebeya.notificationservice.dto.Otpdto;
import et.com.gebeya.notificationservice.telegram.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static et.com.gebeya.notificationservice.util.MessageTemplates.otpMessage;


@Component
@RequiredArgsConstructor
public class SMSService{
    private final TelegramService telegramService;

    public void sendOtp(Otpdto dto) {

        telegramService.sendMessage(dto.getPhoneNo(), otpMessage(dto.getOtp()));
    }

}
