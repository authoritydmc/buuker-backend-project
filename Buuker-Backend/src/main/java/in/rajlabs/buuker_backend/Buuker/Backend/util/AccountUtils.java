package in.rajlabs.buuker_backend.Buuker.Backend.util;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class AccountUtils {
    public static String getAccountID(String customerID, String merchantID) {
        return customerID + "_" + merchantID;
    }


    public static BigDecimal calculateRunningBalance(String accountID) {
        return BigDecimal.valueOf(0);
    }
}
