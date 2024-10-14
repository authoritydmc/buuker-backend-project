package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AccountTransactionDTO {

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;
    @NotNull(message = "amount is required")
    @Min(value = 1, message = "amount cant be less than 1")
    private BigDecimal amount;
    private String description;

    @NotBlank(message = "customerID is required")
    private String customerID;

    @NotBlank(message = "merchantID is required")
    private String merchantID;
}
