package et.com.gebeya.paymentservice.util;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessagingUtil {
    private MessagingUtil(){}
    public static String providerWithdrawalNotification(BigDecimal amount){
        String formattedDateTime = getDateTime();
        return "Dear Customer, you have withdraw "+amount+ " from your wallet at "+formattedDateTime+"/nParkingLotLocator";
    }

    public static String driverDepositNotification(BigDecimal amount){
        String formattedDateTime = getDateTime();
        return "Dear Customer, you have deposit "+amount+ " to you wallet at "+formattedDateTime+"/nParkingLotLocator";
    }

    private static String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return now.format(formatter);
    }

    public static String driverTransferNotification(BigDecimal amount, String providerId){
        String formattedDateTime = getDateTime();
        return "Dear Customer, you have transferred "+amount+" from your wallet to "+providerId+" at "+formattedDateTime+"/nParkingLotLocator";
    }

    public static String providerTransferNotification(BigDecimal amount, String driverId){
        String formattedDateTime = getDateTime();
        return "Dear Customer, you have received "+amount+" from "+driverId+" at "+formattedDateTime+"/nParkingLotLocator";
    }

}
