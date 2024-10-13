package in.rajlabs.buuker_backend.Buuker.Backend.dto;

import in.rajlabs.buuker_backend.Buuker.Backend.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object for retrieving TransactionLedger details.
 */
@Data
@Builder
public class TransactionLedgerOutputDTO {

    /**
     * Unique identifier for the transaction.
     */
    private String transactionID;

    private double buyPrice;

    private double finalReceiveAmount;

    private double commission;

    private int unit;

    private String merchantID;

    private String bookingPlatform;

    private String bookingMobNo;

    private String shippedFrom;
    private String customerID;

    private String shippingTrackingID;

    private boolean isDeleted;

    private long deletedOn;
    private String bookingCardDetails;
    private double runningBalance; 

    /**
     * Epoch timestamp in milliseconds when the transaction was created.
     * Managed automatically by the system.
     */
    private Long createdOn;

    /**
     * Epoch timestamp in milliseconds when the transaction was last updated.
     * Managed automatically by the system.
     */
    private Long updatedOn;


    private String remark;

    private OrderStatus orderStatus;

    private String productName;

    private String productLink;
}
