package et.com.gebeya.paymentservice.util;

public class IdConvertorUtil {
    private IdConvertorUtil() {
    }

    public static String providerConvertor(Integer id) {
        return "PROVIDER_" + id;
    }

    public static String driverConvertor(Integer id) {
        return "DRIVER_" + id;
    }
}
