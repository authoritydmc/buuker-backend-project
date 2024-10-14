package in.rajlabs.buuker_backend.Buuker.Backend.model;

import in.rajlabs.buuker_backend.Buuker.Backend.util.TransactionUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "transaction_ledger")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionLedger {

    @Id
    @Column(name = "transaction_id", nullable = false, unique = true, updatable = false)
    private String transactionID = UUID.randomUUID().toString();


    private double buyPrice;


    private double finalReceiveAmount;


    private double commission;

    private Long deletedOn;
    private boolean isDeleted;

    private int unit;

    private String customerID;

    private String merchantID;

    private String bookingPlatform;

    private String bookingMobNo;


    private String shippedFrom;


    private String shippingTrackingID;

    private double runningBalance;


    private Long createdOn;


    private Long updatedOn;


    private String bookingCardDetails;


    private String remark;

    private OrderStatus orderStatus;

    private String productName;


    private String productLink;

    @PrePersist
    protected void onCreate() {
        this.runningBalance = TransactionUtils.calculateRunningBalance(this.customerID, this.updatedOn);
    }


    @PreUpdate
    protected void onUpdate() {
        this.runningBalance = TransactionUtils.calculateRunningBalance(this.customerID, this.updatedOn);
    }
}
