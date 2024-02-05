package et.com.gebeya.notificationservice.service;

import static et.com.gebeya.notificationservice.util.Constant.OTP_MESSAGE;

public class email implements Message{
    @Override
    public String message(String message) {
        return OTP_MESSAGE(message);
    }
}
