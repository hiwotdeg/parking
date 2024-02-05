package et.com.gebeya.notificationservice.util;

public class MessageTemplates {
    private MessageTemplates(){}
    public static String otpMessage(String message)
    {
        return message + " is your verification code for Parking lot locator app";
    }
}
