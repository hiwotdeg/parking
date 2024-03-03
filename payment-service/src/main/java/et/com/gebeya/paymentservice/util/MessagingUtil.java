package et.com.gebeya.paymentservice.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessagingUtil {
    private MessagingUtil(){}
    public static String providerWithdrawalNotification(BigDecimal amount){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return "Dear Customer, you have withdraw "+amount+ "from you wallet at "+formattedDateTime+"/nParkingLotLocator";
    }

    public static String driverDepositNotification(BigDecimal amount){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        return "Dear Customer, you have deposit "+amount+ "to you wallet at "+formattedDateTime+"/nParkingLotLocator";
    }
}
