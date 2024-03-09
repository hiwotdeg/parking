package et.com.gebeya.notificationservice.telegram;
public class Constants {
    private Constants(){}
    public static final String WELCOME_MESSAGE = "Hello, Welcome to Parking Lot Locator Bot.\n\nPlease Insert your phone number so that we can identify you\nlike +251912345678 or +251712345678";
    public static String RESPONSE_MESSAGE(String phoneNo) {
        return "Thank you for Registering to our bot. Any message that is going to be send to "+phoneNo+" will be forward it to you on this bot.\nThank You!!! Have a good day\nParking Lot Locator App";
    }
    public static final String ERROR_MESSAGE = "You inserted a wrong Phone number. please try again";

}
