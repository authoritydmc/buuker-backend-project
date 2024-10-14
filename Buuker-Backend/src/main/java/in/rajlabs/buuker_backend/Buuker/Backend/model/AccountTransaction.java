package in.rajlabs.buuker_backend.Buuker.Backend.model;

import in.rajlabs.buuker_backend.Buuker.Backend.exception.InvalidTransactionException; // Ensure you have this exception defined
import in.rajlabs.buuker_backend.Buuker.Backend.util.AccountUtils;
import in.rajlabs.buuker_backend.Buuker.Backend.util.TransactionUtils;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity representing a credit or debit transaction on an account.
 */
@Entity
@Table(name = "account_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String accountId;

    private String transactionId;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // CREDIT or DEBIT

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    private String description;

    private Long createdOn;

    private BigDecimal runningBalance;

    private Long updatedOn;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    protected void onCreate() throws InvalidTransactionException {
        validateRunningBalance();
    }

    @PreUpdate
    protected void onUpdate() throws InvalidTransactionException {
        validateRunningBalance();

    }

    /**
     * Validates the runningBalance field.
     *
     * @throws InvalidTransactionException if runningBalance is null
     */
    private void validateRunningBalance() throws InvalidTransactionException {
        if (runningBalance == null) {
            throw new InvalidTransactionException("Running balance cannot be null");
        }
    }
}
