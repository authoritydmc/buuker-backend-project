package in.rajlabs.buuker_backend.Buuker.Backend.util;

import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import in.rajlabs.buuker_backend.Buuker.Backend.repository.AccountTransactionRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

public class AccountUtils {


    public static String getAccountID(String customerID, String merchantID) {
        return customerID + "_" + merchantID;
    }


    public static TransactionType getTransactionTypeFromOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new IllegalArgumentException("OrderStatus cannot be null");
        }

        return switch (orderStatus) {
            case ORDERED, SHIPPED, DELIVERED ->
                    TransactionType.DEBIT; //treat them as debit transaction as money is spent from customer account

            case CANCELLED ->
                    TransactionType.CREDIT; // Treat these statuses as a credit transaction as money is received

        };
    }

}
