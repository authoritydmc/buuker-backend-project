package in.rajlabs.buuker_backend.Buuker.Backend.util;

import jakarta.validation.constraints.NotBlank;

public class AccountUtils {
    public static String getAccountID(String customerID, String merchantID) {
        return customerID + "_" + merchantID;
    }
}
