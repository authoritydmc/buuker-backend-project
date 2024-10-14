package in.rajlabs.buuker_backend.Buuker.Backend.util;

import in.rajlabs.buuker_backend.Buuker.Backend.repository.AccountTransactionRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AccountUtils {


    public static String getAccountID(String customerID, String merchantID) {
        return customerID + "_" + merchantID;
    }


}
