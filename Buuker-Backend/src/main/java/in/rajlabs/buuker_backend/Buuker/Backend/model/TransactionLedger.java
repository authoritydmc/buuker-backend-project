package in.rajlabs.buuker_backend.Buuker.Backend.model;

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

//    @PrePersist as all time details are trusted  from client side +offline support mode for android client
//    protected void onCreate() {
//        long currentEpoch = System.currentTimeMillis();
//        this.createdOn = currentEpoch;
//        this.updatedOn = currentEpoch;
//        this.setDeletedOn(-1L);
//    }
//
//
////    @PreUpdate
////    protected void onUpdate() {
////        this.updatedOn = System.currentTimeMillis();
////    }
}
