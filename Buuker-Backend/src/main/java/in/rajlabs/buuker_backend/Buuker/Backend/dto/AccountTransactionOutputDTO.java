package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.TransactionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO representing the output for account transactions.
 */
@Getter
@Setter
@Builder
public class AccountTransactionOutputDTO {
    
    private UUID id;                       // Unique identifier of the transaction
    private String accountId;              // ID of the associated account
    private TransactionType transactionType; // Type of transaction (CREDIT or DEBIT)
    private BigDecimal amount;              // Amount involved in the transaction
    private String description;             // Description of the transaction
    private Long createdOn;                 // Timestamp when the transaction was created
    private Long updatedOn;                 // Timestamp when the transaction was last updated
    private String createdBy;                // User who created the transaction
    private String updatedBy;                // User who last updated the transaction
    private String transactionId;  //in case of debit we have transactionId for credit it will be null
}
