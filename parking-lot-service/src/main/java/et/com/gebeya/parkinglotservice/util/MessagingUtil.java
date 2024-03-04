package et.com.gebeya.parkinglotservice.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MessagingUtil {
    private MessagingUtil(){}
    public static String providerWithdrawalNotification(){
        return "Dear Provider, You have received a new booking request for your parking lot. click to view the details";
    }

    public static String driverBookNotification(String parkingLotName, LocalTime duration){
        String formattedDateTime = getDateTime();
        return "Dear Customer, You have successfully booked a parking spot at "+parkingLotName+ ". Your booking starts at "+formattedDateTime+"and is valid for a duration of "+getModifiedTime(duration)+".\nThank you for choosing our parking service.";
    }

    private static String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return now.format(formatter);
    }

    private static String getModifiedTime(LocalTime time){
        int hour = time.getHour();
        int minute = time.getMinute();
        return String.format("%02d:%02d", hour, minute);
    }

}
