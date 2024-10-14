package in.rajlabs.buuker_backend.Buuker.Backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    private Long updatedOn;

    private String createdBy;

    private String updatedBy;


    @PrePersist
    protected void onCreate() {
        createdOn = System.currentTimeMillis();
        updatedOn = System.currentTimeMillis();
    }


    @PreUpdate
    protected void onUpdate() {
        updatedOn = System.currentTimeMillis();
    }
}
